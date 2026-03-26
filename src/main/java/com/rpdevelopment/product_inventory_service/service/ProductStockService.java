package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.DTO.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.repository.ProductRepository;
import com.rpdevelopment.product_inventory_service.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockService {

    //======= DEPENDENCIAS ===========

    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public StockRepository stockRepository;


    //======= GET ===========

    //FIND ALL
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAll(Pageable pageable) {
        Page<Product> product = productRepository.findAll(pageable);
        return product.map(ProductStockDTO::new);
    }
}
