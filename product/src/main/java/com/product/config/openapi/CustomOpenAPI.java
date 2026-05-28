package com.product.config.openapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class CustomOpenAPI {

    @Bean
    public OpenAPI productOpenAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("bearerAuth");

        return new OpenAPI()
            .info(new Info()
                .title("DWB - API Product")
                .version("0.0.1")
                .description("API para la gestión de categorías y productos para la tienda en línea FCiencias Store."))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme));
    }

    @Bean
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public OpenApiCustomizer sortSchemasAlphabetically() {
        return openApi -> {
            Components components = openApi.getComponents();
            if (components != null && components.getSchemas() != null) {
                Map<String, Schema> schemas = components.getSchemas();
                List<String> keys = new ArrayList<>(schemas.keySet());
                java.util.Collections.sort(keys);
                Map<String, Schema> sorted = new LinkedHashMap<>();
                for (String key : keys) {
                    sorted.put(key, schemas.get(key));
                }
                components.setSchemas(sorted);
            }
        };
    }
}
