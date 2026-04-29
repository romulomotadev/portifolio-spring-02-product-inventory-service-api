package com.rpdevelopment.product_inventory_service.dto.product;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rpdevelopment.product_inventory_service.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados de um produto")
@JsonPropertyOrder({ "id", "name", "description", "sku", "price", "active"})
public class ProductDTO {

    //========== ATRIBUTOS ==============


    @Schema(description = "ID do produto gerado automaticamente", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Notebook Gamer")
    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;

    @Schema(description = "Descrição detalhada do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    private String description;

    @Schema(description = "Código SKU único do produto", example = "NB-001")
    @NotBlank(message = "SKU é obrigatório")
    private String sku;

    @Schema(description = "Preço do produto", example = "3500.00")
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double price;

    @Schema(description = "Indica se o produto está ativo", example = "true")
    private boolean active;


    //========== CONTRUTORES ==============

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String sku, Double price, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.active = active;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.sku = entity.getSku();
        this.price = entity.getPrice();
        this.active = entity.isActive();
    }

    //========== GETTER | SETTER ==============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
