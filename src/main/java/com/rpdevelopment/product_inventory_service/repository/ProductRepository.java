package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
