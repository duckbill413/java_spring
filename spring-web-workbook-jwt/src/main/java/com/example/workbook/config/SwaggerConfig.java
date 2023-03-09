package com.example.workbook.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author        : duckbill413
 * date          : 2023-03-01
 * description   :
 **/
@Configuration
public class SwaggerConfig {
    private final String JWT_SCHEMA = "Authorization";

    private Info info(){
        Info info = new Info()
                .title("Boot 02 Project Swagger")
                .version("v2.0.1")
                .description("자바 웹 개발 워크북 실습 내용 - JWT");
        return info;
    }

    // Swagger JWT 적용
    @Bean
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT_SCHEMA); // 헤더에 토큰 포함
        Components components = new Components().addSecuritySchemes(JWT_SCHEMA, new SecurityScheme()
                .name(JWT_SCHEMA)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .info(this.info())
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
