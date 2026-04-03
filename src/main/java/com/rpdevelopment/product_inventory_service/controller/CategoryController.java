package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    //============ DEPENDENCIAS ===============

    @Autowired
    private CategoryService service;


    //============ GET ===============

    //FIND ALL
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    //FIND BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    // ========= POST ==========

    // NEW CATEGORY
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }


    // ========= UPDATE ==========

    // UPDATE CATEGORY
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }


    // ========= DELETE ==========

    // DELETE CATEGORY
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
