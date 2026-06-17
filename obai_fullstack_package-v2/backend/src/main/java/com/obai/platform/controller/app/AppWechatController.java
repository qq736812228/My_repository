package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.dto.PaymentNotifyRequest;
import com.obai.platform.dto.SubscribeMessageRequest;
import com.obai.platform.entity.PaymentCallback;
import com.obai.platform.service.PaymentService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/wechat")
public class AppWechatController extends BaseController {
    private final PaymentService paymentService;

    public AppWechatController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay/params")
    public ApiResponse<Map<String, Object>> payParams(@RequestBody Map<String, String> body) {
        return ApiResponse.ok(paymentService.wechatPayParams(body.getOrDefault("orderNo", ""), currentUserId()));
    }

    @PostMapping("/pay/notify")
    public ApiResponse<PaymentCallback> payNotify(@RequestBody PaymentNotifyRequest request) {
        return ApiResponse.ok(paymentService.notify(request));
    }

    @PostMapping("/subscribe-message")
    public ApiResponse<Map<String, Object>> subscribe(@RequestBody SubscribeMessageRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("accepted", true);
        result.put("templateId", request.templateId());
        result.put("scene", request.scene());
        return ApiResponse.ok(result);
    }
}
