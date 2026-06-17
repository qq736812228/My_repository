package com.obai.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "operation_log")
public class OperationLog extends BaseEntity {
    public Long userId;
    public String username;
    public String module;
    public String action;
    public String method;
    public String path;
    public String clientIp;
    public Integer statusCode;
    public Long costMs;
    public String requestId;
    public String remark;
    public Instant operatedAt;
}
