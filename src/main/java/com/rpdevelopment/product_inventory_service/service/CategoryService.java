package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    // =========== UPDATE ==============

    @Transactional
    public CategoryDTO update(CategoryDTO categoryDTO, Long id){
        Category category = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));

        category.setName(categoryDTO.getName());
        repository.save(category);
        return new CategoryDTO(category);
    }


    // ========= DELETE ==========

    // DELETE CATEGORY
    @Transactional
    public void delete(Long id){
        Category category = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found"));
        repository.delete(category);
    }
}
