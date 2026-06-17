package com.obai.platform.repository;

import com.obai.platform.entity.SysUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    Optional<SysUser> findByUsername(String username);
    Optional<SysUser> findByOpenid(String openid);

}
