package com.obai.platform.repository;

import com.obai.platform.entity.HealthArchive;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthArchiveRepository extends JpaRepository<HealthArchive, Long> {
    Optional<HealthArchive> findByUserId(Long userId);

}
