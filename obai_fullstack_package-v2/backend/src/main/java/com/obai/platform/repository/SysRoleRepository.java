package com.obai.platform.repository;

import com.obai.platform.entity.SysRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
    Optional<SysRole> findByCode(String code);

}
