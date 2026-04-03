package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.StockDTO;


public class ProductStockDTO extends ProductDTO {


    //===== ATRIBUTOS RELACIONADOS =======

    private StockDTO stockDTO;


    //========= CONSTRITORES =============

    public ProductStockDTO() {
    }

    public ProductStockDTO(Long id, String name, String description, String sku, Double price, boolean active, StockDTO stock) {
        super(id, name, description, sku, price, active);
        this.stockDTO = stock;
    }

    public ProductStockDTO(Product entity) {
        super(entity);
        this.stockDTO = entity.getStock();
    }


    //====== GETTER | SETTER ========

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
    }
}
