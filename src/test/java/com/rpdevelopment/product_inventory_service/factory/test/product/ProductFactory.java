package com.rpdevelopment.product_inventory_service.factory.test.product;

import com.rpdevelopment.product_inventory_service.dto.product.ProductDTO;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;

import java.util.HashSet;
import java.util.Set;

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createNewCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createValidCategory;

public class ProductFactory {

    //=============== FACTORY =================

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Product createValidProduct() {

        Set<Category> categoryValidFactory = new HashSet<>();
        categoryValidFactory.add(createValidCategory());

        return new Product(
                1L,
                "Novo produto",
                "Descrição novo produto",
                "A00-000",
                100.0,
                true,
                categoryValidFactory);
    }


    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Product createNewProduct() {

        Set<Category> categoryNewFactory = new HashSet<>();
        categoryNewFactory.add(createNewCategory());

        return new Product(
                null,
                "Novo produto",
                "Descrição novo produto",
                "A00-000",
                100.0,
                true,
                categoryNewFactory);
    }


    //=============== DTO =================

    // Para Services (Mockito)
    public static ProductDTO createValidProductDTO() {
        Product productValid = createValidProduct();
        return new ProductDTO(productValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static ProductDTO createNewProductDTO() {
        Product productNew = createNewProduct();
        return new ProductDTO(productNew);
    }
}
