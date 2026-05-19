package com.example.aibackend.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SummaryRequest(
        @NotBlank(message = "text must not be blank")
        String text,
        @Min(value = 1, message = "maxSentences must be at least 1")
        @Max(value = 5, message = "maxSentences must be at most 5")
        int maxSentences
) {
}
