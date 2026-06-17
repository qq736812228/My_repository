package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sys_region")
public class Region extends BaseEntity {
    public String code;
    public String name;
    public String parentCode;
    public String level;
    public Integer sortNo = 0;
}
