package com.obai.platform.dto;

import java.util.List;

public record TokenResponse(String token, Long userId, String username, String nickname, List<String> roles) {
}
