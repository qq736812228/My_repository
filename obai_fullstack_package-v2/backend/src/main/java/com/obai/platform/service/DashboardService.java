package com.obai.platform.service;

import com.obai.platform.repository.*;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final SysUserRepository userRepository;
    private final ProductRepository productRepository;
    private final DetectionOrderRepository detectionOrderRepository;
    private final SelfTestRecordRepository selfTestRecordRepository;
    private final MerchantRepository merchantRepository;

    public DashboardService(SysUserRepository userRepository, ProductRepository productRepository,
                            DetectionOrderRepository detectionOrderRepository, SelfTestRecordRepository selfTestRecordRepository,
                            MerchantRepository merchantRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.detectionOrderRepository = detectionOrderRepository;
        this.selfTestRecordRepository = selfTestRecordRepository;
        this.merchantRepository = merchantRepository;
    }

    public Map<String, Object> overview() {
        return Map.of(
                "userCount", userRepository.count(),
                "productCount", productRepository.count(),
                "detectionOrderCount", detectionOrderRepository.count(),
                "selfTestCount", selfTestRecordRepository.count(),
                "merchantCount", merchantRepository.count(),
                "trend", List.of(
                        Map.of("date", "D-6", "users", 18, "orders", 7),
                        Map.of("date", "D-5", "users", 21, "orders", 9),
                        Map.of("date", "D-4", "users", 30, "orders", 11),
                        Map.of("date", "D-3", "users", 27, "orders", 13),
                        Map.of("date", "D-2", "users", 41, "orders", 18),
                        Map.of("date", "D-1", "users", 36, "orders", 16),
                        Map.of("date", "Today", "users", 52, "orders", 24)
                )
        );
    }
}
