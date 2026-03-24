package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //FIND BY SKU
    Product findBySku(String sku);

    //FIND ALL PRODUCT ACTIVE
    Page<Product> findAllByActive(Pageable pageable, boolean active);

}


