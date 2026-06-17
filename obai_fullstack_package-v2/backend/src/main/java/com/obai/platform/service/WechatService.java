package com.obai.platform.service;

import com.obai.platform.common.BusinessException;
import com.obai.platform.common.HashUtil;
import com.obai.platform.config.WechatProperties;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Service
public class WechatService {
    private final WechatProperties properties;
    private final RestClient restClient;

    public WechatService(WechatProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.create();
    }

    public WechatSession code2Session(String code) {
        if (!StringUtils.hasText(code)) {
            throw new BusinessException(400, "微信登录 code 不能为空");
        }
        if (Boolean.TRUE.equals(properties.getDevFallbackEnabled()) || !configuredForWechatLogin()) {
            return new WechatSession("wx_" + HashUtil.sha256(code).substring(0, 20), null, null);
        }
        Map<?, ?> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.weixin.qq.com")
                        .path("/sns/jscode2session")
                        .queryParam("appid", properties.getAppid())
                        .queryParam("secret", properties.getSecret())
                        .queryParam("js_code", code)
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .retrieve()
                .body(Map.class);
        if (response == null || response.get("openid") == null) {
            Object errcode = response == null ? null : response.get("errcode");
            Object errmsg = response == null ? null : response.get("errmsg");
            throw new BusinessException(401, "微信登录失败：" + errcode + " " + errmsg);
        }
        return new WechatSession(
                String.valueOf(response.get("openid")),
                response.get("session_key") == null ? null : String.valueOf(response.get("session_key")),
                response.get("unionid") == null ? null : String.valueOf(response.get("unionid"))
        );
    }

    private boolean configuredForWechatLogin() {
        return StringUtils.hasText(properties.getAppid())
                && StringUtils.hasText(properties.getSecret())
                && !properties.getAppid().startsWith("demo")
                && !properties.getSecret().startsWith("demo");
    }

    public record WechatSession(String openid, String sessionKey, String unionid) {
    }
}
