package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "point_transaction")
public class PointTransaction extends BaseEntity {
    public Long userId;
    public Integer amount;
    public String type;
    public String source;
    public String refNo;
    public String description;
}
