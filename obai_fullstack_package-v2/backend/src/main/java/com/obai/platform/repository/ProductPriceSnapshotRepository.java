package com.obai.platform.repository;

import com.obai.platform.entity.ProductPriceSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceSnapshotRepository extends JpaRepository<ProductPriceSnapshot, Long> {
    List<ProductPriceSnapshot> findByProductIdOrderByCapturedAtDesc(Long productId);

}
