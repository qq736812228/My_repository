package com.obai.platform.repository;

import com.obai.platform.entity.AiReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiReportRepository extends JpaRepository<AiReport, Long> {
    List<AiReport> findByUserIdOrderByCreatedAtDesc(Long userId);

}
