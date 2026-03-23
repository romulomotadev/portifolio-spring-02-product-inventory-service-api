package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.DTO.ProductDTO;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ProductService {

    //========= DEPENDENCIAS ============

    @Autowired
    private ProductRepository productRepository;


    //============= GET ===============

    //GET ALL
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductDTO::new);
    }

}