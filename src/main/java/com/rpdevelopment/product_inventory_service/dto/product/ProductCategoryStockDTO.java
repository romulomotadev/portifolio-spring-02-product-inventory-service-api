package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.Stock;

import java.util.Set;

public class ProductCategoryStockDTO extends ProductCategoryDTO {

    //===== ATRIBUTOS RELACIONADOS =======

    private Stock stock;


    //========= CONSTRITORES =============

    public ProductCategoryStockDTO(){}

    public ProductCategoryStockDTO(Long id, String name, String description, String sku, Double price, boolean active,
                                   Set<CategoryDTO> categories, Stock stock) {
        super(id, name, description, sku, price, active, categories);
        this.stock = stock;
    }

    public ProductCategoryStockDTO(Product entity){
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
