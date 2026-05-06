package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductStockFactory.createValidProductStockDTO;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductStockFactory.productStockValidFactory;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createValidStockDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTests {

    // ========= DEPENDÊNCIAS =========

    @InjectMocks
    private StockService service;

    @Mock
    private ProductRepository repository;

    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;

    private Product productEntity;
    private ProductStockDTO productDto;
    private StockDTO stockDto;

    private Pageable pageable;
    Page<Product> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 999L;

        // Usando a Factory unificada que você organizou
        productEntity = productStockValidFactory();
        productDto = createValidProductStockDTO();
        stockDto = createValidStockDTO();

        pageable = PageRequest.of(0, 10);

        // Criando a página a partir da entidade da factory
        page = new PageImpl<>(List.of(productEntity), pageable, 1);
    }


    // ========= FIND ALL =========

    @Test
    @DisplayName("findAll deve retornar Page de DTO")
    void findAll_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(repository.findAll(pageable)).thenReturn(page);

        // ACT
        Page<ProductStockDTO> result = service.findAll(pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }


    // ========= FIND BY ID =========

    @Test
    @DisplayName("findById deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIdExists() {

        // ARRANGE
        when(repository.findById(existingId))
                .thenReturn(Optional.of(productEntity));

        // ACT
        ProductStockDTO result = service.findById(existingId);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(result.getName(), productDto.getName());
        assertEquals(result.getDescription(), productDto.getDescription());
        assertEquals(result.getPrice(), productDto.getPrice());
        assertEquals(result.getStockDTO().getId(), productDto.getStockDTO().getId());
        assertEquals(result.getStockDTO().getQuantity(), productDto.getStockDTO().getQuantity());

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

    // ========= FIND ALL STOCK LOW =========

    @Test
    @DisplayName("find All Stock Low deve retornar Page de DTO")
    void findAllStockLow_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(repository.findAllByMinimumStock(pageable)).thenReturn(page);

        // ACT
        Page<ProductStockDTO> result = service.findAllStockLow(pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        verify(repository).findAllByMinimumStock(pageable);
        verifyNoMoreInteractions(repository);
    }


    // ========= FIND ALL PRODUCT OUT STOCK =========

    @Test
    @DisplayName("find All Products Out Stock deve retornar Page de DTO")
    void findAllOutOfStock_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(repository.findAllProductsOutOfStock(pageable)).thenReturn(page);

        // ACT
        Page<ProductStockDTO> result = service.findAllOutOfStock(pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        verify(repository).findAllProductsOutOfStock(pageable);
        verifyNoMoreInteractions(repository);
    }


    // ========= UPDATE =========
    @Test
    @DisplayName("update deve atualizar e retornar DTO quando ID existir")
    void update_ShouldUpdateAndReturnDTO_WhenIdExists() {

        // ARRANGE
        when(repository.findById(existingId)).thenReturn(Optional.of(productEntity));

        when(repository.save(any(Product.class))).thenReturn(productEntity);

        // ACT
        StockDTO result = service.update(existingId, stockDto);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(result.getQuantity(), stockDto.getQuantity());
        assertEquals(result.getMinimum_stock(), stockDto.getMinimum_stock());


        verify(repository).findById(existingId);
        verify(repository).save(any(Product.class));
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
            service.update(nonExistingId, stockDto);
        });

        verify(repository).findById(nonExistingId);
        verifyNoMoreInteractions(repository);
    }
}
