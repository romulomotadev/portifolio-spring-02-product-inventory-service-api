package com.rpdevelopment.product_inventory_service.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rpdevelopment.product_inventory_service.entities.Category;

@JsonPropertyOrder({ "id", "name" })
public class CategoryDTO {

    //========== ATRIBUTOS ==============

    private Long id;
    private String name;


    //========== CONSTRUTORES ==============

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }


    //========== CONSTRUTORES ==============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
