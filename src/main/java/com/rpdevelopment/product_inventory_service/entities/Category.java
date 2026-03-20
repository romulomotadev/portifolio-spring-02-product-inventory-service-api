package com.rpdevelopment.product_inventory_service.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category {

    //========== ATRIBUTOS ==============

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    //===== ATRIBUTOS RELACIONADOS =======

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();


    //========== CONSTRUTORES ==============

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    //======== GETTER | SETTER ===========

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
