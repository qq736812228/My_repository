package com.obai.platform.dto;

public record PaymentNotifyRequest(String orderNo, String payChannel, String transactionId, String status, String rawPayload) {
}
