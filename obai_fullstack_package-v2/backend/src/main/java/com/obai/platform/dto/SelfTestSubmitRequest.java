package com.obai.platform.dto;

import jakarta.validation.constraints.NotBlank;

public record SelfTestSubmitRequest(
        String version,
        String symptomTags,
        String behaviorTags,
        String stoolStatus,
        String sleepStatus,
        String pressureLevel,
        @NotBlank String answerJson) {
}
