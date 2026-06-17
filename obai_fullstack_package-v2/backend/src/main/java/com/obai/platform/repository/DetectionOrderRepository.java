package com.obai.platform.repository;

import com.obai.platform.entity.DetectionOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectionOrderRepository extends JpaRepository<DetectionOrder, Long> {
    List<DetectionOrder> findByUserIdOrderByCreatedAtDesc(Long userId);

}
