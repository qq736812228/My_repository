package com.obai.platform.service;

import com.obai.platform.dto.LeadRequest;
import com.obai.platform.dto.MerchantApplicationRequest;
import com.obai.platform.entity.GroupCustomerLead;
import com.obai.platform.entity.MerchantApplication;
import com.obai.platform.entity.PartnerLead;
import com.obai.platform.repository.GroupCustomerLeadRepository;
import com.obai.platform.repository.MerchantApplicationRepository;
import com.obai.platform.repository.PartnerLeadRepository;
import org.springframework.stereotype.Service;

@Service
public class LeadService {
    private final GroupCustomerLeadRepository groupRepository;
    private final PartnerLeadRepository partnerRepository;
    private final MerchantApplicationRepository merchantApplicationRepository;

    public LeadService(GroupCustomerLeadRepository groupRepository, PartnerLeadRepository partnerRepository,
                       MerchantApplicationRepository merchantApplicationRepository) {
        this.groupRepository = groupRepository;
        this.partnerRepository = partnerRepository;
        this.merchantApplicationRepository = merchantApplicationRepository;
    }

    public GroupCustomerLead groupLead(Long userId, LeadRequest request) {
        GroupCustomerLead lead = new GroupCustomerLead();
        lead.userId = userId;
        lead.organizationName = request.organizationName();
        lead.contactName = request.contactName();
        lead.contactPhone = request.contactPhone();
        lead.remark = request.remark();
        lead.status = "PENDING";
        return groupRepository.save(lead);
    }

    public PartnerLead partnerLead(Long userId, LeadRequest request) {
        PartnerLead lead = new PartnerLead();
        lead.userId = userId;
        lead.partnerName = request.partnerName();
        lead.partnerType = request.partnerType();
        lead.contactName = request.contactName();
        lead.contactPhone = request.contactPhone();
        lead.remark = request.remark();
        lead.status = "PENDING";
        return partnerRepository.save(lead);
    }

    public MerchantApplication merchant(Long userId, MerchantApplicationRequest request) {
        MerchantApplication application = new MerchantApplication();
        application.userId = userId;
        application.merchantName = request.merchantName();
        application.contactName = request.contactName();
        application.contactPhone = request.contactPhone();
        application.licenseNo = request.licenseNo();
        application.status = "PENDING";
        return merchantApplicationRepository.save(application);
    }
}
