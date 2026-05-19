package com.example.aibackend.service.dto;

import java.util.List;

public record SummaryResult(
        String summary,
        int sentenceCount,
        List<String> keywords
) {
}
