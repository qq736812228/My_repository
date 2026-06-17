package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant")
public class Merchant extends BaseEntity {
    public String name;
    public String contactName;
    public String contactPhone;
    public String licenseNo;
    public String status;
    public Integer creditScore = 60;
    public String auditRemark;
}
