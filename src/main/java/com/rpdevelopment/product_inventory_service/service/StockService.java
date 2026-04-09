package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.entity.StockDTO;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.ProductRepository;
import com.rpdevelopment.product_inventory_service.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

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

    //FIND ID
    @Transactional(readOnly = true)
    public ProductStockDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new ProductStockDTO(product);
    }

    //FIND ALL PRODUCT BY STOCK LOW
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAllStockLow(Pageable pageable) {
        Page<Product> products = productRepository.findAllByMinimumStock(pageable);
        return products.map(ProductStockDTO::new);
    }

    //FIND ALL PRODUCTS OUT OF STOCK
    @Transactional(readOnly = true)
    public Page<ProductStockDTO> findAllOutOfStock(Pageable pageable) {
        Page<Product> products = productRepository.findAllProductsOutOfStock(pageable);
        return products.map(ProductStockDTO::new);
    }


    //======= UPDATE ===========

    // UPDATE STOCK BY PRODUCT
    @Transactional
    public com.rpdevelopment.product_inventory_service.dto.stock.StockDTO update(Long id, com.rpdevelopment.product_inventory_service.dto.stock.StockDTO stockDTO) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Resource not found"));
        StockDTO stock = product.getStock();

        stock.setQuantity(stockDTO.getQuantity());
        stock.setMinimum_stock(stockDTO.getMinimum_stock());

        product.setStock(stock);
        productRepository.save(product);
        return new com.rpdevelopment.product_inventory_service.dto.stock.StockDTO(stock);
    }

}
