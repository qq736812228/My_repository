package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.service.AppHomeService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/home")
public class AppHomeController extends BaseController {
    private final AppHomeService appHomeService;

    public AppHomeController(AppHomeService appHomeService) {
        this.appHomeService = appHomeService;
    }

    @GetMapping
    public ApiResponse<Map<String, Object>> home() {
        return ApiResponse.ok(appHomeService.home(optionalUserId()));
    }

    @GetMapping("/official")
    public ApiResponse<Map<String, Object>> official() {
        return ApiResponse.ok(Map.of("title", "官方入口", "url", "https://obai.local/docs", "status", "OPEN"));
    }

    @GetMapping("/announcements")
    public ApiResponse<Object> announcements() {
        return ApiResponse.ok(java.util.List.of(Map.of("title", "OBAI 平台隐私政策更新说明", "type", "公告")));
    }

    @GetMapping("/knowledge")
    public ApiResponse<Object> knowledge() {
        return ApiResponse.ok(java.util.List.of(Map.of("title", "肠道菌群小知识：益生菌与益生元的区别", "type", "科普")));
    }
}
