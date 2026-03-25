package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
