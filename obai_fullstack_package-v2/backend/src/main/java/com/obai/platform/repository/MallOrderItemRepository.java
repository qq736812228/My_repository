package com.obai.platform.repository;

import com.obai.platform.entity.MallOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MallOrderItemRepository extends JpaRepository<MallOrderItem, Long> {
}
