package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.entities.Product;
import com.rpdevelopment.product_inventory_service.projection.ProductCategoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //============= FIND BY SKU ================

    Product findBySku(String sku);


    //========= FIND ALL PRODUCT ACTIVE =========

    Page<Product> findAllByActive(Pageable pageable, boolean active);


    //====== FIND ALL PRODUCTS BY CATEGORY ========

    @Query(nativeQuery = true, value = """
        SELECT TB_PRODUCT.NAME AS name, 
               TB_PRODUCT.DESCRIPTION AS description, 
               TB_PRODUCT.PRICE AS price, 
               TB_PRODUCT.ACTIVE AS active
        FROM TB_PRODUCT
        INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.ID = TB_PRODUCT_CATEGORY.PRODUCT_ID
        INNER JOIN TB_CATEGORY ON TB_PRODUCT_CATEGORY.CATEGORY_ID  = TB_CATEGORY.ID
        WHERE UPPER(TB_CATEGORY.NAME) = UPPER(:categoryName)
        ORDER BY TB_CATEGORY.NAME ASC
    """, countQuery = """
        SELECT COUNT(*)
        FROM TB_PRODUCT
        INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.ID = TB_PRODUCT_CATEGORY.PRODUCT_ID
        INNER JOIN TB_CATEGORY ON TB_PRODUCT_CATEGORY.CATEGORY_ID  = TB_CATEGORY.ID
        WHERE UPPER(TB_CATEGORY.NAME) = UPPER(:categoryName)
    """)
    Page<ProductCategoryProjection> findAllProductByCategory(String categoryName, Pageable pageable);


    //====== FIND BY PRODUCT BY NAME ========

    Page<Product> findByNameContainingIgnoreCaseOrderByNameAsc(String productName, Pageable pageable);
}


