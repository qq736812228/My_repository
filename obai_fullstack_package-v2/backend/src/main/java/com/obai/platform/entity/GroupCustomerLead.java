package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_customer_lead")
public class GroupCustomerLead extends BaseEntity {
    public Long userId;
    public String organizationName;
    public String contactName;
    public String contactPhone;
    public String status;
    public String remark;
}
