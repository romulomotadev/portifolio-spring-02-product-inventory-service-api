package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {

    // ======= DEPENDENCIAS ===========

    @Autowired
    private StockService productStockService;
    @Autowired
    private StockService stockService;


    // ========== GET ==============

    //FIND ALL
    @GetMapping
    public ResponseEntity<Page<ProductStockDTO>> findAll(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAll(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }

    //FIND ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductStockDTO> findById(@PathVariable Long id) {
        ProductStockDTO productStockDTO = productStockService.findById(id);
        return ResponseEntity.ok(productStockDTO);
    }

    //FIND ALL PRODUCT BY STOCK LOW
    @GetMapping(value = "/low")
    public ResponseEntity<Page<ProductStockDTO>> findAllStockLow(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAllStockLow(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }

    //FIND ALL PRODUCTS OUT OF STOCK
    @GetMapping(value = "/out-of-stock")
    public ResponseEntity<Page<ProductStockDTO>> findAllOutOfStock(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAllOutOfStock(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }


    // ========== UPDATE ==============

    // UPDATE STOCK BY PRODUCT
    @PutMapping(value = "/{id}")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @Valid @RequestBody StockDTO  stockDTO) {
        stockDTO = stockService.update(id, stockDTO);
        return ResponseEntity.ok(stockDTO);
    }

}
