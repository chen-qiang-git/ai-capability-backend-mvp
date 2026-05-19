package com.example.aibackend.service.dto;

import jakarta.validation.constraints.NotBlank;

public record AskRequest(
        @NotBlank(message = "document must not be blank")
        String document,
        @NotBlank(message = "question must not be blank")
        String question
) {
}
