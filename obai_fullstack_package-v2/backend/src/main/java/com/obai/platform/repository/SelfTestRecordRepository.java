package com.obai.platform.repository;

import com.obai.platform.entity.SelfTestRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfTestRecordRepository extends JpaRepository<SelfTestRecord, Long> {
    List<SelfTestRecord> findByUserIdOrderByCreatedAtDesc(Long userId);

}
