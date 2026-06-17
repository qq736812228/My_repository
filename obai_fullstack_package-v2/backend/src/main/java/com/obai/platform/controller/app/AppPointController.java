package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.service.PointService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/points")
public class AppPointController extends BaseController {
    private final PointService pointService;

    public AppPointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ApiResponse<Map<String, Object>> summary() {
        return ApiResponse.ok(pointService.summary(currentUserId()));
    }
}
