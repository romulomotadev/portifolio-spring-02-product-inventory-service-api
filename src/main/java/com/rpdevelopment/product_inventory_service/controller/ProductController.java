package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.DTO.ProductDTO;
import com.rpdevelopment.product_inventory_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    //========= DEPENDENCIAS ==============

    @Autowired
    private ProductService productService;


    //========= GET ==============

    //GETALL
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findall(Pageable pageable) {
        Page<ProductDTO> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

}
