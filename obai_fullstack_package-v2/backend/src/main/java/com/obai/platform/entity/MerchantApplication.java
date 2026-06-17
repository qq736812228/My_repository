package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant_application")
public class MerchantApplication extends BaseEntity {
    public Long userId;
    public String merchantName;
    public String contactName;
    public String contactPhone;
    public String licenseNo;
    public String status;
    public String auditRemark;
}
