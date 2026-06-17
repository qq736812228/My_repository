package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_item")
public class TaskItem extends BaseEntity {
    public String code;
    public String title;
    public String description;
    public Integer rewardPoints;
    public Boolean enabled = true;
}
