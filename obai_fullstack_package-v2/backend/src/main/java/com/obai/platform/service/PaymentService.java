package com.obai.platform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obai.platform.common.BusinessException;
import com.obai.platform.common.HashUtil;
import com.obai.platform.config.WechatProperties;
import com.obai.platform.dto.PaymentNotifyRequest;
import com.obai.platform.entity.MallOrder;
import com.obai.platform.entity.PaymentCallback;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.MallOrderRepository;
import com.obai.platform.repository.PaymentCallbackRepository;
import com.obai.platform.repository.SysUserRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PaymentService {
    private final PaymentCallbackRepository callbackRepository;
    private final MallOrderRepository orderRepository;
    private final SysUserRepository userRepository;
    private final WechatProperties wechatProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final SecureRandom secureRandom = new SecureRandom();

    public PaymentService(PaymentCallbackRepository callbackRepository,
                          MallOrderRepository orderRepository,
                          SysUserRepository userRepository,
                          WechatProperties wechatProperties,
                          ObjectMapper objectMapper) {
        this.callbackRepository = callbackRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.wechatProperties = wechatProperties;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> wechatPayParams(String orderNo, Long userId) {
        if (!StringUtils.hasText(orderNo)) {
            throw new BusinessException(400, "订单号不能为空");
        }
        MallOrder order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new BusinessException(404, "订单不存在"));
        if (!order.userId.equals(userId)) {
            throw new BusinessException(403, "无权支付该订单");
        }
        SysUser user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(404, "用户不存在"));
        String prepayId = shouldUseDevPay()
                ? "dev_" + HashUtil.sha256(orderNo).substring(0, 24)
                : createWechatPrepay(order, user);
        return buildJsapiPaymentParams(prepayId);
    }

    @Transactional
    public PaymentCallback notify(PaymentNotifyRequest request) {
        if (request == null || !StringUtils.hasText(request.orderNo())) {
            throw new BusinessException(400, "支付回调缺少订单号");
        }
        PaymentCallback callback = new PaymentCallback();
        callback.orderNo = request.orderNo();
        callback.payChannel = request.payChannel() == null ? "WECHAT" : request.payChannel();
        callback.transactionId = request.transactionId();
        callback.status = request.status();
        callback.rawPayload = request.rawPayload();
        PaymentCallback saved = callbackRepository.save(callback);
        orderRepository.findByOrderNo(request.orderNo()).ifPresent(order -> {
            order.payChannel = callback.payChannel;
            order.transactionId = callback.transactionId;
            if ("SUCCESS".equalsIgnoreCase(callback.status) || "PAID".equalsIgnoreCase(callback.status)) {
                order.status = "PAID";
            } else if (StringUtils.hasText(callback.status)) {
                order.status = callback.status;
            }
            orderRepository.save(order);
        });
        return saved;
    }

    private boolean shouldUseDevPay() {
        return Boolean.TRUE.equals(wechatProperties.getDevFallbackEnabled()) || !configuredForWechatPay();
    }

    private boolean configuredForWechatPay() {
        return StringUtils.hasText(wechatProperties.getAppid())
                && StringUtils.hasText(wechatProperties.getPayMchId())
                && StringUtils.hasText(wechatProperties.getPaySerialNo())
                && StringUtils.hasText(wechatProperties.getPayPrivateKey())
                && StringUtils.hasText(wechatProperties.getPayNotifyUrl());
    }

    private String createWechatPrepay(MallOrder order, SysUser user) {
        if (!StringUtils.hasText(user.openid)) {
            throw new BusinessException(400, "当前用户未绑定微信 openid，无法发起 JSAPI 支付");
        }
        try {
            String path = "/v3/pay/transactions/jsapi";
            String body = objectMapper.writeValueAsString(buildPrepayPayload(order, user));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(wechatProperties.getPayApiHost() + path))
                    .header("Authorization", buildWechatPayAuthorization("POST", path, body))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new BusinessException(502, "微信预支付下单失败：" + response.body());
            }
            JsonNode node = objectMapper.readTree(response.body());
            if (!node.hasNonNull("prepay_id")) {
                throw new BusinessException(502, "微信预支付响应缺少 prepay_id");
            }
            return node.get("prepay_id").asText();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException(502, "微信预支付下单异常：" + ex.getMessage());
        }
    }

    private Map<String, Object> buildPrepayPayload(MallOrder order, SysUser user) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("appid", wechatProperties.getAppid());
        payload.put("mchid", wechatProperties.getPayMchId());
        payload.put("description", wechatProperties.getPayDescription());
        payload.put("out_trade_no", order.orderNo);
        payload.put("notify_url", wechatProperties.getPayNotifyUrl());
        payload.put("amount", Map.of("total", yuanToCent(order.totalAmount), "currency", "CNY"));
        payload.put("payer", Map.of("openid", user.openid));
        return payload;
    }

    private int yuanToCent(BigDecimal amount) {
        BigDecimal safeAmount = amount == null ? BigDecimal.ZERO : amount;
        return safeAmount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValueExact();
    }

    private Map<String, Object> buildJsapiPaymentParams(String prepayId) {
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String nonce = nonce();
        String packageValue = "prepay_id=" + prepayId;
        String signature = shouldUseDevPay()
                ? "dev-sign-" + HashUtil.sha256(prepayId + nonce).substring(0, 32)
                : sign(wechatProperties.getAppid() + "\n" + timestamp + "\n" + nonce + "\n" + packageValue + "\n");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("timeStamp", timestamp);
        params.put("nonceStr", nonce);
        params.put("package", packageValue);
        params.put("signType", "RSA");
        params.put("paySign", signature);
        return params;
    }

    private String buildWechatPayAuthorization(String method, String path, String body) {
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String nonce = nonce();
        String message = method + "\n" + path + "\n" + timestamp + "\n" + nonce + "\n" + body + "\n";
        String signature = sign(message);
        return "WECHATPAY2-SHA256-RSA2048 mchid=\"" + wechatProperties.getPayMchId()
                + "\",nonce_str=\"" + nonce
                + "\",signature=\"" + signature
                + "\",timestamp=\"" + timestamp
                + "\",serial_no=\"" + wechatProperties.getPaySerialNo() + "\"";
    }

    private String sign(String message) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey());
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception ex) {
            throw new BusinessException(500, "微信支付签名失败：" + ex.getMessage());
        }
    }

    private PrivateKey privateKey() throws Exception {
        String key = wechatProperties.getPayPrivateKey().replace("\\n", "\n")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    private String nonce() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
