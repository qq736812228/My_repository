package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.dto.LeadRequest;
import com.obai.platform.dto.MerchantApplicationRequest;
import com.obai.platform.entity.GroupCustomerLead;
import com.obai.platform.entity.MerchantApplication;
import com.obai.platform.entity.PartnerLead;
import com.obai.platform.service.LeadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/leads")
public class AppLeadController extends BaseController {
    private final LeadService leadService;

    public AppLeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping("/group-customer")
    public ApiResponse<GroupCustomerLead> group(@RequestBody LeadRequest request) {
        return ApiResponse.ok(leadService.groupLead(optionalUserId(), request));
    }

    @PostMapping("/partner")
    public ApiResponse<PartnerLead> partner(@RequestBody LeadRequest request) {
        return ApiResponse.ok(leadService.partnerLead(optionalUserId(), request));
    }

    @PostMapping("/merchant-application")
    public ApiResponse<MerchantApplication> merchant(@RequestBody MerchantApplicationRequest request) {
        return ApiResponse.ok(leadService.merchant(optionalUserId(), request));
    }
}
