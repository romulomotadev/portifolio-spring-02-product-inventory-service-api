package com.rpdevelopment.product_inventory_service.repository;

import com.rpdevelopment.product_inventory_service.entities.StockDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockDTO, Long> {
}
