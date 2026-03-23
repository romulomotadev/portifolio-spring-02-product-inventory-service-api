package com.rpdevelopment.product_inventory_service.DTO;

import com.rpdevelopment.product_inventory_service.entities.Category;
import com.rpdevelopment.product_inventory_service.entities.Product;

import java.util.HashSet;
import java.util.Set;

public class ProductDTO {

    //========== ATRIBUTOS ==============

    private Long id;
    private String name;
    private String description;
    private String sku;
    private Double price;
    private boolean active;


    //===== ATRIBUTOS RELACIONADOS =======

    private Set<CategoryDTO> categories = new HashSet<>();


    //========== CONTRUTORES ==============

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String sku, Double price, boolean active, Set<CategoryDTO> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.active = active;
        this.categories = categories;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.sku = entity.getSku();
        this.price = entity.getPrice();
        this.active = entity.isActive();

        for (Category category : entity.getCategories()) {
            this.categories.add(new CategoryDTO(category));
        }
    }


    //========== GETTER | SETTER ==============

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}

