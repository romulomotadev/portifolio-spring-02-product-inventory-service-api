package com.rpdevelopment.product_inventory_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name= "tb_stock")
public class Stock {

    //========== ATRIBUTOS ==============

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Integer minimium_stock;


    //===== ATRIBUTOS RELACIONADOS =======

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;


    //========== CONSTRUTORES ==============

    public Stock() {
    }

    public Stock(Long id, Integer quantity, Integer minimium_stock) {
        this.id = id;
        this.quantity = quantity;
        this.minimium_stock = minimium_stock;
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

    public Integer getMinimium_stock() {
        return minimium_stock;
    }

    public void setMinimium_stock(Integer minimium_stock) {
        this.minimium_stock = minimium_stock;
    }
}
