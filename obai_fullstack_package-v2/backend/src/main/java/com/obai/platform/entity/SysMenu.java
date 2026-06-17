package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sys_menu")
public class SysMenu extends BaseEntity {
    public Long parentId;
    @Column(nullable = false)
    public String title;
    @Column(nullable = false)
    public String path;
    public String component;
    public String icon;
    public Integer sortNo = 0;
    public String permissionCode;
    public Boolean visible = true;
}
