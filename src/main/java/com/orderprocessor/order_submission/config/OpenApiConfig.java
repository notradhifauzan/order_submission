package com.orderprocessor.order_submission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
        .info(new Info()
            .title("Nasi AJ order API")
            .description("An API to submit customer orders")
            .version("v1.0.0"));
    }
}
