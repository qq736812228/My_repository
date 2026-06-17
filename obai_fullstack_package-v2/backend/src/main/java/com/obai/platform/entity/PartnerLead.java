package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "partner_lead")
public class PartnerLead extends BaseEntity {
    public Long userId;
    public String partnerName;
    public String partnerType;
    public String contactName;
    public String contactPhone;
    public String status;
    public String remark;
}
