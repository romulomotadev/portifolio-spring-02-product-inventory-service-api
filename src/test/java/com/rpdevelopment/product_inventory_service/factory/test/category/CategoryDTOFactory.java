package com.rpdevelopment.product_inventory_service.factory.test.category;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entity.Category;

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createNewCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createValidCategory;

public class CategoryDTOFactory {

    // Para Services (Mockito)
    public static CategoryDTO createValidCategoryDTO(){
        Category categoryValid = createValidCategory();
        return new CategoryDTO(categoryValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static CategoryDTO createNewCategoryDTO(){
        Category categoryNew = createNewCategory();
        return new CategoryDTO(categoryNew);
    }

}
