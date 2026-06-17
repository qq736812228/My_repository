package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    public Long merchantId;
    @Column(nullable = false)
    public String name;
    public String category;
    public String brand;
    public String probioticStrains;
    public BigDecimal price;
    public BigDecimal marketMinPrice;
    public BigDecimal marketMaxPrice;
    public Integer pointDeductionLimit = 0;
    public String status;
    public String qualityStatus;
    public String imageUrl;
    @Column(columnDefinition = "text")
    public String description;
}
