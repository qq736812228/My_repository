package com.obai.platform.service;

import com.obai.platform.repository.PointAccountRepository;
import com.obai.platform.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AppHomeService {
    private final ProductRepository productRepository;
    private final PointAccountRepository pointAccountRepository;

    public AppHomeService(ProductRepository productRepository, PointAccountRepository pointAccountRepository) {
        this.productRepository = productRepository;
        this.pointAccountRepository = pointAccountRepository;
    }

    public Map<String, Object> home(Long userId) {
        Integer points = userId == null ? 2680 : pointAccountRepository.findByUserId(userId).map(a -> a.balance).orElse(2680);
        return Map.of(
                "brand", "耦白 OBAI",
                "slogan", "坚持长期主义，奖励健康，奖励贡献。",
                "values", List.of("身体可知", "商品可信", "价格可比"),
                "points", points,
                "pointDeltaToday", 36,
                "quickEntries", List.of(
                        Map.of("code", "group", "title", "团体客户入口"),
                        Map.of("code", "partner", "title", "合作伙伴入口"),
                        Map.of("code", "login", "title", "注册 / 登录"),
                        Map.of("code", "official", "title", "官方入口")),
                "products", productRepository.findByStatusOrderByCreatedAtDesc("ON_SALE").stream().limit(6).toList()
        );
    }
}
