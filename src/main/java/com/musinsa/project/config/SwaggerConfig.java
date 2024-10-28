package com.musinsa.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("무신사 브랜드/상품 API")
                    .description(
                        "브랜드와 상품을 관리하고 최저가를 조회하는 API"
                    )
                    .version("1.0.0")
            );
    }
}
