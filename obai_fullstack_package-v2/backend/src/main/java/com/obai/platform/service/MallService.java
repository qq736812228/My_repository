package com.obai.platform.service;

import com.obai.platform.common.BusinessException;
import com.obai.platform.dto.ProductOrderRequest;
import com.obai.platform.entity.MallOrder;
import com.obai.platform.entity.MallOrderItem;
import com.obai.platform.entity.PointTransaction;
import com.obai.platform.entity.Product;
import com.obai.platform.repository.MallOrderItemRepository;
import com.obai.platform.repository.MallOrderRepository;
import com.obai.platform.repository.PointTransactionRepository;
import com.obai.platform.repository.ProductPriceSnapshotRepository;
import com.obai.platform.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MallService {
    private final ProductRepository productRepository;
    private final ProductPriceSnapshotRepository priceSnapshotRepository;
    private final MallOrderRepository orderRepository;
    private final MallOrderItemRepository orderItemRepository;
    private final PointTransactionRepository pointTransactionRepository;

    public MallService(ProductRepository productRepository, ProductPriceSnapshotRepository priceSnapshotRepository,
                       MallOrderRepository orderRepository, MallOrderItemRepository orderItemRepository,
                       PointTransactionRepository pointTransactionRepository) {
        this.productRepository = productRepository;
        this.priceSnapshotRepository = priceSnapshotRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    public List<Product> products() {
        return productRepository.findByStatusOrderByCreatedAtDesc("ON_SALE");
    }

    public Map<String, Object> priceCompare(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(404, "商品不存在"));
        return Map.of("product", product, "snapshots", priceSnapshotRepository.findByProductIdOrderByCapturedAtDesc(productId));
    }

    @Transactional
    public MallOrder createOrder(Long userId, ProductOrderRequest request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new BusinessException(400, "订单商品不能为空");
        }
        MallOrder order = new MallOrder();
        order.userId = userId;
        order.orderNo = "MO" + System.currentTimeMillis();
        order.status = "CREATED";
        order.payChannel = "WECHAT";
        order.pointsUsed = request.pointsUsed() == null ? 0 : request.pointsUsed();
        order.totalAmount = BigDecimal.ZERO;
        MallOrder saved = orderRepository.save(order);
        BigDecimal total = BigDecimal.ZERO;
        for (ProductOrderRequest.Item item : request.items()) {
            Product product = productRepository.findById(item.productId()).orElseThrow();
            MallOrderItem orderItem = new MallOrderItem();
            orderItem.orderId = saved.id;
            orderItem.productId = product.id;
            orderItem.productName = product.name;
            orderItem.quantity = item.quantity() == null ? 1 : item.quantity();
            orderItem.unitPrice = product.price;
            orderItemRepository.save(orderItem);
            total = total.add(product.price.multiply(BigDecimal.valueOf(orderItem.quantity)));
        }
        saved.totalAmount = total;
        if (saved.pointsUsed > 0) {
            PointTransaction transaction = new PointTransaction();
            transaction.userId = userId;
            transaction.amount = -saved.pointsUsed;
            transaction.type = "USE";
            transaction.source = "MALL_ORDER";
            transaction.refNo = saved.orderNo;
            transaction.description = "商城订单积分抵扣";
            pointTransactionRepository.save(transaction);
        }
        return orderRepository.save(saved);
    }
}
