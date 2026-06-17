package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation extends BaseEntity {
    public Long inviterUserId;
    public Long inviteeUserId;
    public String inviteCode;
    public String status;
    public Integer rewardPoints = 0;
}
