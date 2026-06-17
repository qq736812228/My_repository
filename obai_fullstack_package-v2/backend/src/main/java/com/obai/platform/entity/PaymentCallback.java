package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_callback")
public class PaymentCallback extends BaseEntity {
    public String orderNo;
    public String payChannel;
    public String transactionId;
    public String status;
    @Column(columnDefinition = "text")
    public String rawPayload;
}
