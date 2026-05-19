package com.example.aibackend.api;

import com.example.aibackend.service.AiCapabilityService;
import com.example.aibackend.service.dto.AskAnswer;
import com.example.aibackend.service.dto.AskRequest;
import com.example.aibackend.service.dto.SummaryRequest;
import com.example.aibackend.service.dto.SummaryResult;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiCapabilityService aiCapabilityService;

    public AiController(AiCapabilityService aiCapabilityService) {
        this.aiCapabilityService = aiCapabilityService;
    }

    @PostMapping("/summary")
    public ApiResponse<SummaryResult> summarize(@Valid @RequestBody SummaryRequest request) {
        SummaryResult result = aiCapabilityService.summarize(request);
        return ApiResponse.success(result, MDC.get("traceId"));
    }

    @PostMapping("/qa")
    public ApiResponse<AskAnswer> answer(@Valid @RequestBody AskRequest request) {
        AskAnswer result = aiCapabilityService.answer(request);
        return ApiResponse.success(result, MDC.get("traceId"));
    }
}
