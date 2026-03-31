package com.rpdevelopment.product_inventory_service.dto.stock;

import com.rpdevelopment.product_inventory_service.entities.Stock;

public class StockDTO {

    //======== ATRIBUTOS =============

    private Long id;
    private Integer quantity;
    private Integer minimum_stock;


    //======== CONSTRUTORES =============

    public StockDTO() {
    }

    public StockDTO(Long id, Integer quantity, Integer minimum_stock) {
        this.id = id;
        this.quantity = quantity;
        this.minimum_stock = minimum_stock;
    }

    public StockDTO(Stock entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.minimum_stock = entity.getMinimium_stock();
    }


    //======== GETTER | SETTER =============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(Integer minimum_stock) {
        this.minimum_stock = minimum_stock;
    }
}
