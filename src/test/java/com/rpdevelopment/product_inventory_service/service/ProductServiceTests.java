package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.entity.Category;
import com.rpdevelopment.product_inventory_service.entity.Product;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.repository.CategoryRepository;
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

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createValidCategory;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryFactory.*;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryStockFactory.createProductCategoryStockValidFactoryDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    // ========= DEPENDÊNCIAS =========

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;

    private String sku;
    private boolean active;
    private String categoryName;
    private String productName;

    private Product entityProductCategory;
    private Category entityCategory;

    private ProductCategoryProjection entityProjection;

    private ProductCategoryDTO categoryDto;
    private ProductCategoryStockDTO categoryStockDto;

    private Pageable pageable;
    Page<Product> page;
    Page<ProductCategoryProjection> pageProjection;


    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 999L;

        sku = "sku";
        active = true;
        categoryName = "category";
        productName = "product";

        entityCategory = createValidCategory();
        entityProductCategory = createProductCategoryValidFactory();
        entityProjection = createProductCategoryProjectionValidFactory();

        categoryDto = createProductCategoryValidFactoryDTO();
        categoryStockDto = createProductCategoryStockValidFactoryDTO();

        pageable = PageRequest.of(0, 10);

        // Criando a página a partir da entidade da factory
        page = new PageImpl<>(List.of(entityProductCategory), pageable, 1);
        pageProjection = new PageImpl<>(List.of(entityProjection), pageable, 1);
    }


    // ========= FIND ALL =========

    @Test
    @DisplayName("findAll deve retornar Page de DTO")
    void findAll_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(productRepository.findAll(pageable)).thenReturn(page);

        // ACT
        Page<ProductCategoryDTO> result = service.findAllProducts(pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findAll(pageable);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= FIND BY ID =========

    @Test
    @DisplayName("findById deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIDExists() {

        // ARRANGE
        when(productRepository.findById(existingId))
                .thenReturn(Optional.of(entityProductCategory));

        // ACT
        ProductCategoryDTO result = service.findById(existingId);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(result.getId(), existingId);
        assertEquals(result.getName(), entityProductCategory.getName());
        assertEquals(result.getDescription(), entityProductCategory.getDescription());
        assertEquals(result.getPrice(), entityProductCategory.getPrice());
        assertEquals(result.getCategories().size(), categoryDto.getCategories().size());
        assertEquals(result.getCategories(), categoryDto.getCategories());

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findById(existingId);
        verifyNoMoreInteractions(productRepository);
    }


    @Test
    @DisplayName("findById deve lançar exceção quando ID não existir")
    void findById_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(productRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= FIND By SKU =========

    @Test
    @DisplayName("findBySku deve retornar DTO quando SKU existir")
    void findBySku_ShouldReturnDTO_WhenSkuExists() {

        // ARRANGE
        when(productRepository.findBySku(sku)).thenReturn(entityProductCategory);

        // ACT
        ProductCategoryDTO result = service.findBySku(sku);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(result.getId(), existingId);
        assertEquals(result.getName(), entityProductCategory.getName());
        assertEquals(result.getDescription(), entityProductCategory.getDescription());
        assertEquals(result.getSku(), entityProductCategory.getSku());
        assertEquals(result.getPrice(), entityProductCategory.getPrice());
        assertEquals(result.getCategories().size(), categoryDto.getCategories().size());
        assertEquals(result.getCategories(), categoryDto.getCategories());

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findBySku(sku);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= FIND ALL PRODUCTS ACTIVE =========

    @Test
    @DisplayName("findAllProductActive deve retornar Page de DTO")
    void findAllProductActive_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(productRepository.findAllByActive(pageable, active)).thenReturn(page);

        // ACT
        Page<ProductCategoryDTO> result = service.findAllProductActive(pageable, active);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findAllByActive(pageable, active);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= FIND ALL PRODUCTS BY CATEGORY =========

    @Test
    @DisplayName("findAllProductByCategory deve retornar Page de DTO")
    void findAllProductByCategory_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(productRepository.findAllProductByCategory(categoryName, pageable)).thenReturn(pageProjection);

        // ACT
        Page<ProductCategoryProjection> result = service.findAllProductByCategory(categoryName, pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getName(), entityProjection.getName());
        assertEquals(result.getContent().getFirst().getDescription(), entityProjection.getDescription());
        assertEquals(result.getContent().getFirst().getPrice(), entityProjection.getPrice());

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findAllProductByCategory(categoryName, pageable);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= FIND ALL PRODUCTS BY NAME (SEARCH) =========

    @Test
    @DisplayName("searchByName deve retornar Page de DTO")
    void findAllProductByName_ShouldReturnPageOfDTO() {

        // ARRANGE
        when(productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(productName, pageable)).thenReturn(page);

        // ACT
        Page<ProductCategoryDTO> result = service.searchByName(productName, pageable);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getSize(), 10);
        assertEquals(result.getContent().getFirst().getId(), existingId);

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findByNameContainingIgnoreCaseOrderByNameAsc(productName, pageable);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= CREATE =========

    @Test
    @DisplayName("insert deve salvar e retornar DTO")
    void insert_ShouldSaveAndReturnDTO() {

        // ARRANGE
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(entityCategory));
        when(productRepository.save(any(Product.class))).thenReturn(entityProductCategory);

        // ACT
        ProductCategoryStockDTO result = service.insert(categoryStockDto);

        // ASSERT
        assertNotNull(result);
        assertEquals(entityProductCategory.getId(), result.getId());
        assertEquals(entityProductCategory.getName(), result.getName());
        assertEquals(entityProductCategory.getDescription(), result.getDescription());
        assertEquals(entityProductCategory.getPrice(), result.getPrice());
        assertEquals(result.getCategories(), categoryDto.getCategories());

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }


    // ========= UPDATE =========

    @Test
    @DisplayName("update deve atualizar e retornar DTO quando ID existir")
    void update_ShouldUpdateAndReturnDTO_WhenIdExists() {

        // ARRANGE
        when(productRepository.findById(existingId))
                .thenReturn(Optional.of(entityProductCategory));

        when(categoryRepository.findAll())
                .thenReturn(List.of(entityCategory));

        when(productRepository.save(any(Product.class)))
                .thenReturn(entityProductCategory);


        // ACT
        ProductCategoryDTO result = service.update(categoryStockDto, existingId);

        // ASSERT
        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals(entityProductCategory.getName(), result.getName());
        assertEquals(entityProductCategory.getDescription(), result.getDescription());
        assertEquals(entityProductCategory.getPrice(), result.getPrice());
        assertEquals(result.getCategories(), categoryDto.getCategories());

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findById(existingId);
        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }


    @Test
    @DisplayName("update deve lançar exceção quando ID não existir")
    void update_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(productRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(categoryStockDto, nonExistingId);
        });

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }


    // ========= DELETE =========

    @Test
    @DisplayName("delete deve remover quando ID existir")
    void delete_ShouldRemove_WhenIdExists() {

        // ARRANGE
        when(productRepository.findById(existingId))
                .thenReturn(Optional.of(entityProductCategory));

        doNothing().when(productRepository).delete(entityProductCategory);

        // ACT
        service.delete(existingId);

        // ASSERT
        verify(productRepository).findById(existingId);
        verify(productRepository).delete(entityProductCategory);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("delete deve lançar exceção quando ID não existir")
    void delete_ShouldThrowException_WhenIdDoesNotExist() {

        // ARRANGE
        when(productRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        //VERIFICAÇÕES DE INTERAÇÃO
        verify(productRepository).findById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }

}
