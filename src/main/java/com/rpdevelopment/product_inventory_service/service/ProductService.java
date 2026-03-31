package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.entities.Category;
import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.entities.Stock;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.repository.CategoryRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;


    //============= GET ===============

    //FIND ALL
    @Transactional(readOnly = true)
    public Page<ProductCategoryDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductCategoryDTO::new);
    }


    //FIND BY ID
    @Transactional(readOnly = true)
    public ProductCategoryDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->  new ResourceNotFoundException("Resource not found"));
        return new ProductCategoryDTO(product);
    }


    //FIND BY SKU
    @Transactional(readOnly = true)
    public ProductCategoryDTO findAllBySku(String sku) {
        Product product = productRepository.findBySku(sku);
        return new ProductCategoryDTO(product);
    }

    //FIND ALL PRODUCT ACTIVE
    @Transactional(readOnly = true)
    public Page<ProductCategoryDTO> findAllProductActive(Pageable pageable, boolean active) {
        Page<Product> productsAtive = productRepository.findAllByActive(pageable, active);
        return productsAtive.map(ProductCategoryDTO::new);
    }

    //FIND ALL PRODUCT BY CATEGORY
    @Transactional(readOnly = true)
    public Page<ProductCategoryProjection> findAllProductByCategory(String categoryName, Pageable pageable) {
        return productRepository.findAllProductByCategory(categoryName, pageable);
    }

    //FIND ALL PRODUCTS BY NAME (SEARCH)
    @Transactional(readOnly = true)
    public Page<ProductCategoryDTO> searchByName(String productName, Pageable pageable) {
        Page<Product> searchByName = productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(productName, pageable);
        return searchByName.map(ProductCategoryDTO::new);
    }


    //============= POST ===============

    @Transactional
    public ProductCategoryStockDTO insert(ProductCategoryStockDTO productCategoryStockDTO) {

        Product product = new Product();
        product.setName(productCategoryStockDTO.getName());
        product.setDescription(productCategoryStockDTO.getDescription());
        product.setSku(productCategoryStockDTO.getSku());
        product.setPrice(productCategoryStockDTO.getPrice());
        product.setActive(productCategoryStockDTO.isActive());

        for (CategoryDTO categoryDTO : productCategoryStockDTO.getCategories()) {

            Category category = categoryRepository.findById(categoryDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            product.getCategories().add(category);
        }

        Stock stock = new Stock();
        stock.setQuantity((productCategoryStockDTO.getStock().getQuantity()));
        stock.setMinimum_stock(productCategoryStockDTO.getStock().getMinimum_stock());
        stock.setProduct(product);

        product.setStock(stock);

        product = productRepository.save(product);

        return new ProductCategoryStockDTO(product);
    }

}