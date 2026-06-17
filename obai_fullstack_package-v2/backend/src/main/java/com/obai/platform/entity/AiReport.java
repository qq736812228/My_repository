package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ai_report")
public class AiReport extends BaseEntity {
    public Long userId;
    public String reportNo;
    public String reportType;
    public Integer stabilityScore;
    public String riskLevel;
    @Column(columnDefinition = "text")
    public String conclusion;
    @Column(columnDefinition = "text")
    public String suggestions;
    public String status;
}
