package com.rpdevelopment.product_inventory_service.factory.test.product;

import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createNewCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createValidCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createNewProduct;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createValidProduct;

public class ProductCategoryFactory {

    // ============= FACTORY ===============

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Product createProductCategoryValidFactory(){

        Set<Category> category = new HashSet<>();
        category.add(createValidCategory());

        Product product = createValidProduct();

        product.getCategories().addAll(category);

        return product;
    }


    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Product productCategoryNewFactory(){

        Set<Category> category = new HashSet<>();
        category.add(createNewCategory());

        Product product = createNewProduct();

        product.getCategories().addAll(category);

        return product;
    }


    // ============= FACTORY PROJECTION===============

    // Instância única para criar as projeções
    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static ProductCategoryProjection createProductCategoryProjectionValidFactory(){

        Map<String, Object> projectionData = new HashMap<>();

        projectionData.put("name", createNewProduct().getName());
        projectionData.put("description", createNewProduct().getDescription());
        projectionData.put("price", createNewProduct().getPrice());
        projectionData.put("active", createNewProduct().isActive());

        return factory.createProjection(ProductCategoryProjection.class, projectionData);

    }


    // ============= DTO ===============

    // Para Services (Mockito)
    public static ProductCategoryDTO createProductCategoryValidDTO() {
        Product productCategoryValid = createProductCategoryValidFactory();
        return new ProductCategoryDTO(productCategoryValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static ProductCategoryDTO productCategoryNewFactoryDTO() {
        Product productCategoryNew = productCategoryNewFactory();
        return new ProductCategoryDTO(productCategoryNew);
    }

}
