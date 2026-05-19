package com.example.aibackend.service.dto;

import java.util.List;

public record AskAnswer(
        String answer,
        List<String> evidence,
        String confidence
) {
}
