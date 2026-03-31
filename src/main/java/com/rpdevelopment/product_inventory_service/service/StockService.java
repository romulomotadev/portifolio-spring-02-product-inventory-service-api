package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    //======= DEPENDENCIAS ===========

    @Autowired
    public ProductRepository repository;


    //======= GET ===========

    //FIND ALL
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAll(Pageable pageable) {
        Page<Product> product = repository.findAll(pageable);
        return product.map(ProductStockDTO::new);
    }

    //FIND ID
    @Transactional(readOnly = true)
    public ProductStockDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new ProductStockDTO(product);
    }

    //FIND ALL PRODUCT BY STOCK LOW
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAllStockLow(Pageable pageable) {
        Page<Product> products = repository.findAllByMinimumStock(pageable);
        return products.map(ProductStockDTO::new);
    }

    //FIND ALL PRODUCTS OUT OF STOCK
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAllOutOfStock(Pageable pageable) {
        Page<Product> products = repository.findAllProductsOutOfStock(pageable);
        return products.map(ProductStockDTO::new);
    }

}
