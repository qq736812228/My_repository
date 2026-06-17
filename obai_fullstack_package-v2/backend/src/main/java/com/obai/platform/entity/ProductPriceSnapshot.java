package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product_price_snapshot")
public class ProductPriceSnapshot extends BaseEntity {
    public Long productId;
    public String channelName;
    public BigDecimal price;
    public String url;
    public Instant capturedAt;
}
