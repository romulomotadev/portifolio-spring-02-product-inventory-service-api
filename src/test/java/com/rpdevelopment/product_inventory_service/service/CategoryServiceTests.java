package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    // ========= DEPENDÊNCIAS =========

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;

    private Category entity;
    private CategoryDTO dto;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 999L;

        entity = new Category(1L, "Acessórios");
        dto = new CategoryDTO(entity);
    }

    // ========= FIND BY ID =========

    @Test
    @DisplayName("findById deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIdExists() {

        // ARRANGE
        when(repository.findById(existingId))
                .thenReturn(Optional.of(entity));

        // ACT
        CategoryDTO result = service.findById(existingId);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(entity.getName(), result.getName());

        verify(repository).findById(existingId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findById deve lançar exceção quando ID não existir")
    void findById_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(repository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        verify(repository).findById(nonExistingId);
        verifyNoMoreInteractions(repository);
    }

    // ========= FIND ALL =========

    @Test
    @DisplayName("findAll deve retornar lista de DTO")
    void findAll_ShouldReturnListOfDTO() {

        // ARRANGE
        when(repository.findAll())
                .thenReturn(List.of(entity));

        // ACT
        List<CategoryDTO> result = service.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findAll deve retornar lista vazia quando não houver dados")
    void findAll_ShouldReturnEmptyList_WhenNoData() {

        // ARRANGE
        when(repository.findAll())
                .thenReturn(List.of());

        // ACT
        List<CategoryDTO> result = service.findAll();

        // ASSERT
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    // ========= CREATE =========

    @Test
    @DisplayName("Save deve salvar e retornar DTO")
    void save_ShouldSaveAndReturnDTO() {

        // ARRANGE
        when(repository.save(any(Category.class)))
                .thenReturn(entity);

        // ACT
        CategoryDTO result = service.save(dto);

        // ASSERT
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());

        verify(repository).save(any(Category.class));
        verifyNoMoreInteractions(repository);
    }

    // ========= UPDATE =========

    @Test
    @DisplayName("update deve atualizar e retornar DTO quando ID existir")
    void update_ShouldUpdateAndReturnDTO_WhenIdExists() {

        // ARRANGE
        when(repository.findById(existingId))
                .thenReturn(Optional.of(entity));

        when(repository.save(any(Category.class)))
                .thenReturn(entity);

        // ACT
        CategoryDTO result = service.update(existingId, dto);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(entity.getName(), result.getName());

        verify(repository).findById(existingId);
        verify(repository).save(any(Category.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("update deve lançar exceção quando ID não existir")
    void update_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(repository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });

        verify(repository).findById(nonExistingId);
        verifyNoMoreInteractions(repository);
    }

    // ========= DELETE =========

    @Test
    @DisplayName("delete deve remover quando ID existir")
    void delete_ShouldRemove_WhenIdExists() {

        // ARRANGE
        when(repository.findById(existingId))
                .thenReturn(Optional.of(entity));

        doNothing().when(repository).delete(entity);

        // ACT
        service.delete(existingId);

        // ASSERT
        verify(repository).findById(existingId);
        verify(repository).delete(entity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("delete deve lançar exceção quando ID não existir")
    void delete_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(repository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        verify(repository).findById(nonExistingId);
        verifyNoMoreInteractions(repository);
    }

}