package com.obai.platform.controller.app;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.dto.ProductOrderRequest;
import com.obai.platform.entity.MallOrder;
import com.obai.platform.entity.Product;
import com.obai.platform.service.MallService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/mall")
public class AppMallController extends BaseController {
    private final MallService mallService;

    public AppMallController(MallService mallService) {
        this.mallService = mallService;
    }

    @GetMapping("/products")
    public ApiResponse<List<Product>> products() {
        return ApiResponse.ok(mallService.products());
    }

    @GetMapping("/products/{id}/price-compare")
    public ApiResponse<Map<String, Object>> priceCompare(@PathVariable Long id) {
        return ApiResponse.ok(mallService.priceCompare(id));
    }

    @PostMapping("/orders")
    public ApiResponse<MallOrder> createOrder(@RequestBody ProductOrderRequest request) {
        return ApiResponse.ok(mallService.createOrder(currentUserId(), request));
    }
}
