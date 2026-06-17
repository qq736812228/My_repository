package com.obai.platform.dto;

import java.time.LocalDate;

public record BehaviorSubmitRequest(LocalDate recordDate, String dietTags, String exerciseTags, String sleepHours,
                                    String stoolFrequency, String pressureLevel, String note) {
}
