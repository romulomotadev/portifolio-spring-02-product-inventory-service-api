package com.rpdevelopment.product_inventory_service.factory.test.product;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createNewProduct;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createValidProduct;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createNewStock;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createValidStock;

public class ProductStockFactory {

    // ============= FACTORY ===============

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Product productStockValidFactory(){

        Product product = createValidProduct();
        Stock stock = createValidStock();

        product.setStock(stock);

        return product;
    }


    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Product productStockNewFactory(){

        Product product = createNewProduct();
        Stock stock = createNewStock();

        stock.setProduct(product);
        product.setStock(stock);

        return product;
    }


    // ============= DTO ===============

    // Para Services (Mockito)
    public static ProductStockDTO createValidProductStockDTO() {
        Product productStockValid = productStockValidFactory();
        return new ProductStockDTO(productStockValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static ProductStockDTO createNewProductStockDTO() {
        Product productStockNew = productStockNewFactory();
        return new ProductStockDTO(productStockNew);
    }

}
