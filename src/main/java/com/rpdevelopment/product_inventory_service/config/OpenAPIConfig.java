package com.rpdevelopment.product_inventory_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
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

}