package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "health_archive")
public class HealthArchive extends BaseEntity {
    public Long userId;
    public String gender;
    public LocalDate birthday;
    public String heightCm;
    public String weightKg;
    public String dietPreference;
    public String sleepPattern;
    public String bowelHabit;
    public String chronicHistory;
    public String allergyHistory;
    public String redFlagNote;
    public Integer completenessScore = 0;
}
