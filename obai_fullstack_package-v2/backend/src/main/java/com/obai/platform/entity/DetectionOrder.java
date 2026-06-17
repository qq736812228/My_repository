package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "detection_order")
public class DetectionOrder extends BaseEntity {
    public Long userId;
    public String orderNo;
    public String sampleNo;
    public String testType;
    public String status;
    public String institutionName;
    public Instant sampledAt;
    public Instant reportedAt;
}
