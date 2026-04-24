package com.rpdevelopment.product_inventory_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {

    //========== ATRIBUTOS ==============

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String sku;
    private Double price;
    private boolean active;


    //===== ATRIBUTOS RELACIONADOS =======

    //CATEGORY
    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    //STOCK
    @JsonManagedReference
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private StockDTO stock;


    //== ATRIBUTOS AUTOMATICOS | LOGS ======

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;


    //========== CONSTRUTORES ==============

    public Product() {
    }

    public Product(Long id, String name, String description, String sku,
                   Double price, boolean active, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.active = active;
        this.categories = categories;
    }


    //========== METODOS ==============

    // LOGS POST
    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    // LOGS UPDATE
    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }


    //======= GETTER | SETTER ==========

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

    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }

    public Set<Category> getCategories() {
        return categories;
    }


    //======= GETTER LOGS ==========

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    //======= EQUALS | HASHCODE ==========

    // NAME
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
