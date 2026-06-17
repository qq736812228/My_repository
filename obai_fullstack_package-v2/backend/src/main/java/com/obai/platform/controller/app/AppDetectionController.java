package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.dto.DetectionOrderRequest;
import com.obai.platform.entity.DetectionOrder;
import com.obai.platform.entity.DetectionReport;
import com.obai.platform.service.DetectionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/detection")
public class AppDetectionController extends BaseController {
    private final DetectionService detectionService;

    public AppDetectionController(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/orders")
    public ApiResponse<DetectionOrder> create(@RequestBody DetectionOrderRequest request) {
        return ApiResponse.ok(detectionService.createOrder(currentUserId(), request));
    }

    @GetMapping("/orders")
    public ApiResponse<List<DetectionOrder>> orders() {
        return ApiResponse.ok(detectionService.orders(currentUserId()));
    }

    @GetMapping("/reports")
    public ApiResponse<List<DetectionReport>> reports() {
        return ApiResponse.ok(detectionService.reports(currentUserId()));
    }
}
