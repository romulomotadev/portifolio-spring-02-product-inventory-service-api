package com.rpdevelopment.product_inventory_service.dto.product;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;

import java.util.HashSet;
import java.util.Set;


public class ProductCategoryDTO extends ProductDTO {


    //===== ATRIBUTOS RELACIONADOS =======

    private Set<CategoryDTO> categories = new HashSet<>();


    //========== CONTRUTORES ==============

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(Long id, String name, String description, String sku, Double price, boolean active,
                              Set<CategoryDTO> categories) {
        super(id, name, description, sku, price, active);
        this.categories = categories;
    }

    public ProductCategoryDTO(Product entity) {
        super(entity);
        for (Category category : entity.getCategories()) {
            this.categories.add(new CategoryDTO(category));
        }
    }


    //========== GETTER | SETTER ==============

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}

