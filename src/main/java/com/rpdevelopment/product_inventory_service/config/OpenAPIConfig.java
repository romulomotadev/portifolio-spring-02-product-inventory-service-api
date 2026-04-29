package com.rpdevelopment.product_inventory_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenAPIConfig {

    @Bean
    public OpenAPI userviewAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product & Inventory Service API")
                        .description("Product & Inventory Reference Project")
                        .version("v0.0.1")
                        .license(new License()
                                .name("GitHub")
                                .url("https://github.com/romulomotadev/portifolio-spring-02-product-inventory-service-api")));
    }


    @Bean
    public OpenApiCustomizer globalSecurityOpenApiCustomizer() { // Mudou para Z
        return openApi -> openApi.getPaths().values()
                .forEach(pathItem -> pathItem.readOperations()
                        .forEach(op -> op.addSecurityItem(new SecurityRequirement().addList("bearerAuth")))
                );
    }

}