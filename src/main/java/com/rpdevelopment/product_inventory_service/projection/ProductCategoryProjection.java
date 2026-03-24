package com.rpdevelopment.product_inventory_service.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "description", "price", "active"})
public interface ProductCategoryProjection {

    //FIND ALL PRODUCT BY CATEGORY
    String getName();
    String getDescription();
    Double getPrice();
    boolean isActive();

}
