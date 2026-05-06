package com.rpdevelopment.product_inventory_service.factory.test.stock;

import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.Stock;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createNewProduct;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createValidProduct;

public class StockFactory {

    // Para Services (Mockito)
    // Já vem com IDs para simular objetos que "vieram do banco"
    public static Stock createValidStock() {

        Product validProduct = createValidProduct();

        return new Stock(
                1L,
                100,
                10,
                validProduct
        );
    }


    // Para Repositories e Integração (@DataJpaTest)
    // O banco de dados vai gerar os IDs
    public static Stock createNewStock() {

        Product newProduct = createNewProduct();

        return new Stock(
                null,
                100,
                10,
                newProduct
        );
    }
}
