package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;
import jakarta.validation.Valid;


public class ProductStockDTO extends ProductDTO {


    //===== ATRIBUTOS RELACIONADOS =======

    @Valid
    private StockDTO stockDTO;


    //========= CONSTRITORES =============

    public ProductStockDTO() {
    }

    public ProductStockDTO(Long id, String name, String description, String sku, Double price, boolean active, StockDTO stockDTO) {
        super(id, name, description, sku, price, active);
        this.stockDTO = stockDTO;
    }


    public ProductStockDTO(Product entity) {
        super(entity);
        this.stockDTO = new StockDTO(entity.getStock());
    }


    //====== GETTER | SETTER ========

    public @Valid StockDTO getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(@Valid StockDTO stockDTO) {
        this.stockDTO = stockDTO;
    }
}
