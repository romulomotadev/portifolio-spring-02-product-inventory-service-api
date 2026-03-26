package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.DTO.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stocks")
public class ProductStockController {

    // ======= DEPENDENCIAS ===========

    @Autowired
    private ProductStockService productStockService;


    // ========== GET ==============

    //FIND ALL
    @GetMapping
    public ResponseEntity<Page<ProductStockDTO>> findAll(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAll(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }
}
