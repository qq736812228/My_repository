package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_review")
public class ProductReview extends BaseEntity {
    public Long productId;
    public Long userId;
    public Integer rating;
    public String tags;
    @Column(columnDefinition = "text")
    public String content;
    public Boolean verifiedPurchase = false;
}
