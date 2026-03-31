package com.rpdevelopment.product_inventory_service.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name= "tb_stock")
public class Stock {

    //========== ATRIBUTOS ==============

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Integer minimum_stock;


    //===== ATRIBUTOS RELACIONADOS =======

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;


    //========== CONSTRUTORES ==============

    public Stock() {
    }

    public Stock(Long id, Integer quantity, Integer minimum_stock, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.minimum_stock = minimum_stock;
        this.product = product;
    }


    //======== GETTER | SETTER ===========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(Integer minimum_stock) {
        this.minimum_stock = minimum_stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
