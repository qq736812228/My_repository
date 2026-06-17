package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mall_order_item")
public class MallOrderItem extends BaseEntity {
    public Long orderId;
    public Long productId;
    public String productName;
    public Integer quantity;
    public BigDecimal unitPrice;
}
