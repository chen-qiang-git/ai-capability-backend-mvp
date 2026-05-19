package com.example.aibackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("AI Capability Backend MVP")
                .description("Interview MVP with summary and document QA APIs")
                .version("v1.0.0")
                .contact(new Contact().name("Candidate Demo Project")));
    }
}
