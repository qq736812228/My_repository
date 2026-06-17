package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "point_account")
public class PointAccount extends BaseEntity {
    public Long userId;
    public Integer balance = 0;
    public Integer totalEarned = 0;
    public Integer totalUsed = 0;
}
