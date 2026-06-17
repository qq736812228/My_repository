package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mall_order")
public class MallOrder extends BaseEntity {
    public Long userId;
    public String orderNo;
    public BigDecimal totalAmount;
    public Integer pointsUsed = 0;
    public String status;
    public String payChannel;
    public String transactionId;
}
