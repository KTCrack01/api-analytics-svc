package com.kt.api_analytics_svc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Analytics Service API")
                        .description("메시지 대시보드 데이터 분석을 위한 REST API")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("KT Team")
                                .email("support@kt.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api-analytics.kt.com")
                                .description("Production Server")
                ));
    }
}
