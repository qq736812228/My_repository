package com.obai.platform.dto;

import java.util.List;

public record ProductOrderRequest(List<Item> items, Integer pointsUsed) {
    public record Item(Long productId, Integer quantity) {
    }
}
