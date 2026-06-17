package com.obai.platform.dto;

import jakarta.validation.constraints.NotBlank;

public record WechatLoginRequest(@NotBlank String code, String encryptedData, String iv, String nickname, String avatarUrl) {
}
