package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_message")
public class NotificationMessage extends BaseEntity {
    public Long userId;
    public String title;
    @Column(columnDefinition = "text")
    public String content;
    public String type;
    public Boolean readFlag = false;
}
