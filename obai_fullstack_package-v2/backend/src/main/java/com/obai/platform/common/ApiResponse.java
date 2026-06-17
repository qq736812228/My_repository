package com.obai.platform.common;

public record ApiResponse<T>(int code, String message, T data, String traceId) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "OK", data, RequestContext.traceId());
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(0, "OK", null, RequestContext.traceId());
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null, RequestContext.traceId());
    }
}
