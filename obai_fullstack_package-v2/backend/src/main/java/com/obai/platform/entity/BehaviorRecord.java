package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "behavior_record")
public class BehaviorRecord extends BaseEntity {
    public Long userId;
    public LocalDate recordDate;
    public String dietTags;
    public String exerciseTags;
    public String sleepHours;
    public String stoolFrequency;
    public String pressureLevel;
    @Column(columnDefinition = "text")
    public String note;
}
