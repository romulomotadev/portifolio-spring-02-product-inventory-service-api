package com.rpdevelopment.product_inventory_service.factory.test.category;

import com.rpdevelopment.product_inventory_service.entity.Category;

public class CategoryFactory {

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Category createValidCategory() {

        return new Category(
                1L, "Nova Categoria");
    }

    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Category createNewCategory() {

        return new Category(
                null, "Nova Categoria");
    }
}
