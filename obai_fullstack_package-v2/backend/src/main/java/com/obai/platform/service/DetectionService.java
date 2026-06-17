package com.obai.platform.service;

import com.obai.platform.dto.DetectionOrderRequest;
import com.obai.platform.entity.DetectionOrder;
import com.obai.platform.entity.DetectionReport;
import com.obai.platform.repository.DetectionOrderRepository;
import com.obai.platform.repository.DetectionReportRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetectionService {
    private final DetectionOrderRepository orderRepository;
    private final DetectionReportRepository reportRepository;

    public DetectionService(DetectionOrderRepository orderRepository, DetectionReportRepository reportRepository) {
        this.orderRepository = orderRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public DetectionOrder createOrder(Long userId, DetectionOrderRequest request) {
        DetectionOrder order = new DetectionOrder();
        order.userId = userId;
        order.orderNo = "DT" + System.currentTimeMillis();
        order.sampleNo = "S" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        order.testType = request.testType() == null ? "16S" : request.testType();
        order.institutionName = request.institutionName() == null ? "OBAI 合作检测机构" : request.institutionName();
        order.status = "CREATED";
        order.sampledAt = Instant.now();
        return orderRepository.save(order);
    }

    public List<DetectionOrder> orders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<DetectionReport> reports(Long userId) {
        return reportRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
