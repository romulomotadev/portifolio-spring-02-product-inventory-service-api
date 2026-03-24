package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.DTO.ProductDTO;
import com.rpdevelopment.product_inventory_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    //========= DEPENDENCIAS ==============

    @Autowired
    private ProductService productService;


    //========= GET ==============

    //FIND ALL
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findall(Pageable pageable) {
        Page<ProductDTO> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    //FIND BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productId = productService.findById(id);
        return ResponseEntity.ok(productId);
    }

    //FIND BY SKY
    @GetMapping(value = "/sku")
    public ResponseEntity<ProductDTO> findBySku(
            @RequestParam(name = "sku") String sku ) {
        ProductDTO productSku = productService.findAllBySku(sku);
        return ResponseEntity.ok(productSku);
    }

}
