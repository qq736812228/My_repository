package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "detection_report")
public class DetectionReport extends BaseEntity {
    public Long orderId;
    public Long userId;
    public String reportNo;
    public Integer healthScore;
    public String dominantSpecies;
    public String diversityLevel;
    @Column(columnDefinition = "text")
    public String summary;
    @Column(columnDefinition = "text")
    public String rawJson;
}
