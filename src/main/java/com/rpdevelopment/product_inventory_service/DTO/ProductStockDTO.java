package com.rpdevelopment.product_inventory_service.DTO;

import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.Stock;

public class ProductStockDTO {

    //========== ATRIBUTOS ==============

    private Long id;
    private String name;
    private String description;
    private String sku;
    private Double price;
    private boolean active;


    //===== ATRIBUTOS RELACIONADOS =======

    private Stock stock;


    //========= CONSTRITORES =============

    public ProductStockDTO() {
    }

    public ProductStockDTO(Long id, String name, String description, String sku, Double price, boolean active, Stock stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.active = active;
        this.stock = stock;
    }

    public ProductStockDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.sku = entity.getSku();
        this.price = entity.getPrice();
        this.active = entity.isActive();

        this.stock = entity.getStock();
    }


    //====== GETTER | SETTER ========

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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
