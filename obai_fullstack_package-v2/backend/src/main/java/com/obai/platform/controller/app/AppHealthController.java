package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.dto.BehaviorSubmitRequest;
import com.obai.platform.dto.SelfTestSubmitRequest;
import com.obai.platform.entity.BehaviorRecord;
import com.obai.platform.entity.HealthArchive;
import com.obai.platform.entity.SelfTestRecord;
import com.obai.platform.service.HealthService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/health")
public class AppHealthController extends BaseController {
    private final HealthService healthService;

    public AppHealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/archive")
    public ApiResponse<HealthArchive> archive() {
        return ApiResponse.ok(healthService.archive(currentUserId()));
    }

    @PutMapping("/archive")
    public ApiResponse<HealthArchive> saveArchive(@RequestBody HealthArchive archive) {
        return ApiResponse.ok(healthService.saveArchive(archive, currentUserId()));
    }

    @PostMapping("/self-tests")
    public ApiResponse<SelfTestRecord> submitSelfTest(@Valid @RequestBody SelfTestSubmitRequest request) {
        return ApiResponse.ok(healthService.submitSelfTest(currentUserId(), request));
    }

    @GetMapping("/self-tests")
    public ApiResponse<List<SelfTestRecord>> selfTests() {
        return ApiResponse.ok(healthService.selfTests(currentUserId()));
    }

    @PostMapping("/behaviors")
    public ApiResponse<BehaviorRecord> behavior(@RequestBody BehaviorSubmitRequest request) {
        return ApiResponse.ok(healthService.submitBehavior(currentUserId(), request));
    }

    @GetMapping("/behaviors")
    public ApiResponse<List<BehaviorRecord>> behaviors() {
        return ApiResponse.ok(healthService.behaviors(currentUserId()));
    }
}
