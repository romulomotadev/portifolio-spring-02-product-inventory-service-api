package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;
import jakarta.validation.Valid;


public class ProductStockDTO extends ProductDTO {


    //===== ATRIBUTOS RELACIONADOS =======

    @Valid
    private Stock stockDTO;


    //========= CONSTRITORES =============

    public ProductStockDTO() {
    }

    public ProductStockDTO(Long id, String name, String description, String sku, Double price, boolean active, Stock stock) {
        super(id, name, description, sku, price, active);
        this.stockDTO = stock;
    }

    public ProductStockDTO(Product entity) {
        super(entity);
        this.stockDTO = entity.getStock();
    }


    //====== GETTER | SETTER ========

    public Stock getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(Stock stockDTO) {
        this.stockDTO = stockDTO;
    }
}
