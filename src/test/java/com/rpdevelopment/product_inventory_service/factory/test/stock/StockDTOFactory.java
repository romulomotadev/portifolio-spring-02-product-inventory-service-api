package com.rpdevelopment.product_inventory_service.factory.test.stock;

import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;

import com.rpdevelopment.product_inventory_service.entity.Stock;

import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createNewStock;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createValidStock;

public class StockDTOFactory {

    // Para Services (Mockito)
    public static StockDTO ValidStockDTO() {
        Stock stockValid = createValidStock();
        return new StockDTO(stockValid);
    }

    // Para Repositories e Integração (@DataJpaTest)
    public static StockDTO createNewProductDTO() {
        Stock stockNew = createNewStock();
        return new StockDTO(stockNew);
    }
}
