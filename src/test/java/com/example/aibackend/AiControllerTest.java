package com.example.aibackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void summarizeShouldReturnStructuredJson() throws Exception {
        mockMvc.perform(post("/api/v1/ai/summary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "text": "Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.",
                                  "maxSentences": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Trace-Id"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.summary").value(org.hamcrest.Matchers.containsString("Spring Boot helps teams build APIs quickly.")))
                .andExpect(jsonPath("$.data.sentenceCount").value(2))
                .andExpect(jsonPath("$.data.keywords").isArray());
    }

    @Test
    void qaShouldReturnAnswerAndEvidence() throws Exception {
        mockMvc.perform(post("/api/v1/ai/qa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "document": "The project provides text summary and document QA endpoints. It also includes logging, validation, and exception handling. Swagger UI is enabled for demo.",
                                  "question": "Which AI capabilities does the project provide?"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.answer").value(org.hamcrest.Matchers.containsString("text summary and document QA endpoints")))
                .andExpect(jsonPath("$.data.evidence[0]").exists())
                .andExpect(jsonPath("$.data.confidence").value("high"));
    }

    @Test
    void invalidSummaryRequestShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/ai/summary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "text": "",
                                  "maxSentences": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.traceId").isNotEmpty());
    }
}
