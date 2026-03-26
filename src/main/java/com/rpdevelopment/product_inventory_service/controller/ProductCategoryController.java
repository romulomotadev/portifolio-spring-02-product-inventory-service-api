package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.DTO.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
public class ProductCategoryController {

    //========= DEPENDENCIAS ==============

    @Autowired
    private ProductCategoryService productService;


    //========= GET ==============

    //FIND ALL
    @GetMapping
    public ResponseEntity<Page<ProductCategoryDTO>> findall(Pageable pageable) {
        Page<ProductCategoryDTO> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    //FIND BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductCategoryDTO> findById(@PathVariable Long id) {
        ProductCategoryDTO productId = productService.findById(id);
        return ResponseEntity.ok(productId);
    }

    //FIND BY SKU
    @GetMapping(value = "/sku")
    public ResponseEntity<ProductCategoryDTO> findBySku(
            @RequestParam(name = "sku") String sku ) {
        ProductCategoryDTO productSku = productService.findAllBySku(sku);
        return ResponseEntity.ok(productSku);
    }

    //FIND ALL PRODUCT ACTIVE
    @GetMapping(value = "/active")
    public ResponseEntity<Page<ProductCategoryDTO>> findAllByActive(
            @RequestParam(name = "active", defaultValue = "true")
            boolean active, Pageable pageable) {
        Page<ProductCategoryDTO> productActive = productService.findAllProductActive(pageable, active);
        return ResponseEntity.ok(productActive);
    }

    //FIND ALL PRODUCTS BY CATEGORY
    @GetMapping(value = "/category")
    public ResponseEntity<Page<ProductCategoryProjection>> findAllByCategory(
            @RequestParam(name = "category", defaultValue = "")
            String category, Pageable pageable){
        Page<ProductCategoryProjection> productByCategory = productService.findAllProductByCategory(category, pageable);
        return ResponseEntity.ok(productByCategory);
    }

    //FIND ALL PRODUCTS BY NAME (SEARCH)
    @GetMapping(value = "/name")
    public ResponseEntity<Page<ProductCategoryDTO>> seachByName(
            @RequestParam(name = "name", defaultValue = "")
            String name, Pageable pageable) {
        Page<ProductCategoryDTO> searchByName = productService.searchByName(name, pageable);
        return ResponseEntity.ok(searchByName);
    }
}
