package com.obai.platform.repository;

import com.obai.platform.entity.MallOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MallOrderRepository extends JpaRepository<MallOrder, Long> {
    List<MallOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<MallOrder> findByOrderNo(String orderNo);
}
