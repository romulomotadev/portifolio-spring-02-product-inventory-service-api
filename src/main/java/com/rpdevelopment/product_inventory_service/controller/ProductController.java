package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.DTO.ProductDTO;
import com.rpdevelopment.product_inventory_service.projection.ProductCategoryProjection;
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

    //FIND ALL PRODUCT ACTIVE
    @GetMapping(value = "/active")
    public ResponseEntity<Page<ProductDTO>> findAllByActive(
            @RequestParam(name = "active", defaultValue = "true")
            boolean active, Pageable pageable) {
        Page<ProductDTO> productActive = productService.findAllProductActive(pageable, active);
        return ResponseEntity.ok(productActive);
    }

    @GetMapping(value = "/category")
    public ResponseEntity<Page<ProductCategoryProjection>> findAllByCategory(
            @RequestParam(name = "category", defaultValue = "")
            String category, Pageable pageable){
        Page<ProductCategoryProjection> productByCategory = productService.findAllProductByCategory(category, pageable);
        return ResponseEntity.ok(productByCategory);
    }
}
