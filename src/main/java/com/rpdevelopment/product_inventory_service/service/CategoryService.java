package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.DTO.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entities.Category;
import com.rpdevelopment.product_inventory_service.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    // =========== DEPENDENCIA ==============

    @Autowired
    private CategoryRepository repository;


    // =========== GET ==============

    //FIND ALL
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> categories = repository.findAll();
        return categories.stream().map(CategoryDTO::new).toList();
    }

    //FIND BY ID
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found")
        );
        return new CategoryDTO(category);
    }


    // =========== POST ==============

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO){
        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        Category savedCategory = repository.save(newCategory);
        return new CategoryDTO(savedCategory);
    }

}
