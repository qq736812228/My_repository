package com.obai.platform.repository;

import com.obai.platform.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByCreatedAtDesc(String status);

}
