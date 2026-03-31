package com.rpdevelopment.product_inventory_service.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.Stock;

public class StockDTO {

    //======== ATRIBUTOS =============

    private Long id;
    private Integer quantity;
    private Integer minimum_stock;


    //======== ATRIBUTOS RELACIONADOS =============

    @JsonIgnore
    private Product product;


    //======== CONSTRUTORES =============

    public StockDTO() {
    }

    public StockDTO(Long id, Integer quantity, Integer minimum_stock, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.minimum_stock = minimum_stock;
        this.product = product;
    }

    public StockDTO(Stock entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.minimum_stock = entity.getMinimum_stock();
        this.product = entity.getProduct();
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
