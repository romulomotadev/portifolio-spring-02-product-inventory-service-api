package com.rpdevelopment.product_inventory_service.dto.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Projeção de dados de produtos filtrados por categoria")
@JsonPropertyOrder({ "name", "description", "price", "active"})
public interface ProductCategoryProjection {

    @Schema(description = "Nome do produto", example = "Notebook Gamer")
    String getName();

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    String getDescription();

    @Schema(description = "Preço do produto", example = "3500.00")
    Double getPrice();

    @Schema(description = "Indica se o produto está ativo", example = "true")
    boolean isActive();
}
