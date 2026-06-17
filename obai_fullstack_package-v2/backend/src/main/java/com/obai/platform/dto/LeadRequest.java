package com.obai.platform.dto;

public record LeadRequest(String organizationName, String partnerName, String partnerType, String contactName, String contactPhone, String remark) {
}
