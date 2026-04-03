package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.entities.Product;

import java.util.Set;

public class ProductCategoryStockDTO extends ProductCategoryDTO {

    //===== ATRIBUTOS RELACIONADOS =======

    private StockDTO stock;


    //========= CONSTRITORES =============

    public ProductCategoryStockDTO(){}

    public ProductCategoryStockDTO(Long id, String name, String description, String sku, Double price, boolean active, Set<CategoryDTO> categories, StockDTO stock) {
        super(id, name, description, sku, price, active, categories);
        this.stock = stock;
    }

    public ProductCategoryStockDTO(Product entity){
        super(entity);
        this.stock = new StockDTO(entity.getStock());
    }


    //====== GETTER | SETTER ========


    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }
}
