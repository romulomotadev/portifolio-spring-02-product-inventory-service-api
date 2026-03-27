package com.rpdevelopment.product_inventory_service.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.Stock;


public class ProductStockDTO extends ProductDTO {


    //===== ATRIBUTOS RELACIONADOS =======

    private Stock stock;


    //========= CONSTRITORES =============

    public ProductStockDTO() {
    }

    public ProductStockDTO(Long id, String name, String description, String sku, Double price, boolean active, Stock stock) {
        super(id, name, description, sku, price, active);
        this.stock = stock;
    }

    public ProductStockDTO(Product entity) {
        super(entity);
        entity.setStock(stock);
    }


    //====== GETTER | SETTER ========

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
