package com.example.apiitem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemSwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HypeLink ITEM API 명세서")
                        .description("GPS기반 재고관리 및 프렌차이즈 주문 관리 시스템의 아이템부분")
                        .version("0.1.0"));
    }
}
