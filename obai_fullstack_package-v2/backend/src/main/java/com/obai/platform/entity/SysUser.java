package com.obai.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {
    @Column(nullable = false, unique = true)
    public String username;
    @Column(nullable = false)
    public String passwordHash;
    public String nickname;
    public String phone;
    public String avatarUrl;
    public String openid;
    public String status = "ENABLED";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<SysRole> roles = new HashSet<>();
}
