package com.rpdevelopment.product_inventory_service.factory.test.product;

import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;
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
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createNewStock;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createValidStock;

public class ProductCategoryStockFactory {

    // ============= FACTORY ===============

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Product createProductCategoryStockValidFactory(){

        Set<Category> category = new HashSet<>();
        category.add(createValidCategory());

        Stock stock = createValidStock();

        Product product = createValidProduct();

        product.getCategories().addAll(category);
        product.setStock(stock);

        return product;
    }


    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Product createProductCategoryStockNewFactory(){

        Set<Category> category = new HashSet<>();
        category.add(createNewCategory());

        Stock stock = createNewStock();

        Product product = createNewProduct();

        product.getCategories().addAll(category);
        product.setStock(stock);

        return product;
    }


    // ============= DTO ===============

    // Para Services (Mockito)
    public static ProductCategoryStockDTO createProductCategoryStockValidFactoryDTO() {
        Product productCategorySockValid = createProductCategoryStockValidFactory();
        return new ProductCategoryStockDTO(productCategorySockValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static ProductCategoryStockDTO createProductCategoryStockNewFactoryDTO() {
        Product productCategoryStockNew = createProductCategoryStockNewFactory();
        return new ProductCategoryStockDTO(productCategoryStockNew);
    }

}
