package com.obai.platform.repository;

import com.obai.platform.entity.PointAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointAccountRepository extends JpaRepository<PointAccount, Long> {
    Optional<PointAccount> findByUserId(Long userId);

}
