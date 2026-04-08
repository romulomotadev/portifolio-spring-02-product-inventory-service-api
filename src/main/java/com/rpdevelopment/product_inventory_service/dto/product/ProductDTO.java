package com.rpdevelopment.product_inventory_service.dto.product;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rpdevelopment.product_inventory_service.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonPropertyOrder({ "id", "name", "description", "sku", "price", "active"})
public class ProductDTO {

    //========== ATRIBUTOS ==============

    private Long id;

    @NotBlank(message = "Campo nome produto requerido")
    private String name;

    private String description;

    @NotBlank(message = "Campo SKU requerido")
    private String sku;

    @NotNull(message = "Campo preço requerido")
    @Positive(message = "Preço deve ser maior que zero")
    private Double price;
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
