package com.obai.platform.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank String username, @NotBlank String password, String nickname, String phone) {
}
