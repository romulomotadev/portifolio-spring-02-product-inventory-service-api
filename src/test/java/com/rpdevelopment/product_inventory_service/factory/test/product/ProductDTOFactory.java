package com.rpdevelopment.product_inventory_service.factory.test.product;

import com.rpdevelopment.product_inventory_service.dto.product.ProductDTO;
import com.rpdevelopment.product_inventory_service.entity.Product;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createNewProduct;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createValidProduct;

public class ProductDTOFactory {

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

