package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "self_test_record")
public class SelfTestRecord extends BaseEntity {
    public Long userId;
    public String version;
    public String symptomTags;
    public String behaviorTags;
    public String stoolStatus;
    public String sleepStatus;
    public String pressureLevel;
    @Column(columnDefinition = "text")
    public String answerJson;
    public Integer score = 0;
    public String riskLevel;
    public String adviceSummary;
}
