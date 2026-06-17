package com.obai.platform.repository;

import com.obai.platform.entity.PaymentCallback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCallbackRepository extends JpaRepository<PaymentCallback, Long> {
}
