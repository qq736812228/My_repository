package com.obai.platform.repository;

import com.obai.platform.entity.BehaviorRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorRecordRepository extends JpaRepository<BehaviorRecord, Long> {
    List<BehaviorRecord> findByUserIdOrderByCreatedAtDesc(Long userId);

}
