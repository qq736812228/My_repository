package com.obai.platform.repository;

import com.obai.platform.entity.DetectionReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectionReportRepository extends JpaRepository<DetectionReport, Long> {
    List<DetectionReport> findByUserIdOrderByCreatedAtDesc(Long userId);

}
