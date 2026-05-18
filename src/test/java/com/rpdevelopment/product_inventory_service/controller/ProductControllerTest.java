package com.rpdevelopment.product_inventory_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductDTO;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryFactory.createProductCategoryProjectionValidFactory;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryFactory.createProductCategoryValidDTO;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryStockFactory.createProductCategoryStockValidFactoryDTO;
import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductFactory.createValidProductDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductControllerTest {

    // ========= DEPENDÊNCIAS =========

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService service;


    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;
    private String sku;
    private boolean active;
    private String categoryName;
    private String productName;

    private ProductDTO dto;
    private ProductCategoryDTO categoryDTO;
    private ProductCategoryProjection categoryProjection;
    private ProductCategoryStockDTO categoryStockDTO;

    private Pageable pageable;
    private Page<ProductDTO> page;
    private Page<ProductCategoryDTO> pageCategory;
    private Page<ProductCategoryProjection> pageCategoryProjection;
    private Page<ProductCategoryStockDTO> pageCategoryStock;


    @BeforeEach
    void setUp() {

        existingId = 1L;
        nonExistingId = 999L;
        sku = "A001-P001";
        active = true;
        categoryName = "Componentes";
        productName = "Processador";

        dto = createValidProductDTO();
        categoryDTO = createProductCategoryValidDTO();
        categoryProjection = createProductCategoryProjectionValidFactory();
        categoryStockDTO = createProductCategoryStockValidFactoryDTO();

        pageable = PageRequest.of(0, 10);

        page = new PageImpl<>(List.of(dto), pageable, 1);
        pageCategory = new PageImpl<>(List.of(categoryDTO), pageable, 1);
        pageCategoryProjection = new PageImpl<>(List.of(categoryProjection), pageable, 1);
        pageCategoryStock = new PageImpl<>(List.of(categoryStockDTO), pageable, 1);

    }


    // ========= FIND ALL =========

    @Test
    @DisplayName("GET /products deve retornar página de DTO")
    void findAll_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(service.findAllProducts(any())).thenReturn(pageCategory);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(categoryDTO.getName()))
                .andExpect(jsonPath("$.content[0].description").value(categoryDTO.getDescription()))
                .andExpect(jsonPath("$.content[0].categories[0].id").value(categoryDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.content[0].categories[0].name").value(categoryDTO.getCategories().iterator().next().getName()));
    }


    // ========= FIND BY ID =========

    @Test
    @DisplayName("GET /products/{id} deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(service.findById(existingId))
                .thenReturn(categoryDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.description").value(categoryDTO.getDescription()))
                .andExpect(jsonPath("$.categories[0].id").value(categoryDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.categories[0].name").value(categoryDTO.getCategories().iterator().next().getName()));
    }


    @Test
    @DisplayName("GET /products/{id} deve retornar 404 quando ID não existir")
    void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(service.findById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }


    // ========= FIND BY SKU =========

    @Test
    @DisplayName("GET /products/{sku} deve retornar DTO quando ID existir")
    void findBySku_ShouldReturnDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(service.findBySku(sku))
                .thenReturn(categoryDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/sku")
                        .param("sku", sku)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()))
                .andExpect(jsonPath("$.sku").value(categoryDTO.getSku()))
                .andExpect(jsonPath("$.categories[0].id").value(categoryDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.categories[0].name").value(categoryDTO.getCategories().iterator().next().getName()));
    }


    // ========= FIND ALL PRODUCT ACTIVE =========

    @Test
    @DisplayName("GET /products/{active} deve retornar página de DTO")
    void findAllProductActive_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(service.findAllProductActive(any(Pageable.class), eq(active)))
                .thenReturn(pageCategory);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/active")
                        .param("active", String.valueOf(active))
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(categoryDTO.getName()))
                .andExpect(jsonPath("$.content[0].active").value(categoryDTO.isActive()))
                .andExpect(jsonPath("$.content[0].categories[0].id").value(categoryDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.content[0].categories[0].name").value(categoryDTO.getCategories().iterator().next().getName()));
    }


    // ========= FIND ALL PRODUCTS BY CATEGORY =========

    @Test
    @DisplayName("GET /products/{category} deve retornar página de DTO")
    void findAllProductByCategory_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(service.findAllProductByCategory(eq(categoryName) , any(Pageable.class)))
                .thenReturn(pageCategoryProjection);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/category")
                        .param("category", categoryName)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.content[0].name").value(dto.getName()))
                .andExpect(jsonPath("$.content[0].description").value(dto.getDescription()))
                .andExpect(jsonPath("$.content[0].price").value(dto.getPrice()))
                .andExpect(jsonPath("$.content[0].active").value(active));
    }


    // ========= FIND ALL PRODUCTS BY NAME (SEARCH) =========

    @Test
    @DisplayName("GET /products/{name} deve retornar página de DTO")
    void searchByName_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(service.searchByName(eq(productName), any(Pageable.class)))
                .thenReturn(pageCategory);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/products/name")
                        .param("name", String.valueOf(productName))
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(categoryDTO.getName()))
                .andExpect(jsonPath("$.content[0].active").value(categoryDTO.isActive()))
                .andExpect(jsonPath("$.content[0].categories[0].id").value(categoryDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.content[0].categories[0].name").value(categoryDTO.getCategories().iterator().next().getName()));
    }


    // ========= CREATE =========

    @Test
    @DisplayName("POST /products deve retornar DTO criado")
    void create_ShouldReturnCreatedDTO() throws Exception {

        // ========= ARRANGE =========

        when(service.insert(any(ProductCategoryStockDTO.class)))
                .thenReturn(categoryStockDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryStockDTO)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(categoryStockDTO.getName()))
                .andExpect(jsonPath("$.description").value(categoryStockDTO.getDescription()))
                .andExpect(jsonPath("$.categories[0].id").value(categoryStockDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.categories[0].name").value(categoryStockDTO.getCategories().iterator().next().getName()));
    }


    // ========= UPDATE =========

    @Test
    @DisplayName("PUT /products/{id} deve atualizar e retornar DTO")
    void update_ShouldReturnUpdatedDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(service.update(any(ProductCategoryDTO.class), eq(existingId)))
                .thenReturn(categoryDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/products/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()))
                .andExpect(jsonPath("$.description").value(categoryStockDTO.getDescription()))
                .andExpect(jsonPath("$.categories[0].id").value(categoryStockDTO.getCategories().iterator().next().getId()))
                .andExpect(jsonPath("$.categories[0].name").value(categoryStockDTO.getCategories().iterator().next().getName()));
    }


    @Test
    @DisplayName("PUT /products/{id} deve retornar 404 quando ID não existir")
    void update_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(service.update(any(ProductCategoryDTO.class), eq(nonExistingId)))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/products/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))

                .andExpect(status().isNotFound());
    }


    // ========= DELETE =========

    @Test
    @DisplayName("DELETE /products/{id} deve retornar 204 quando ID existir")
    void delete_ShouldReturnNoContent_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        doNothing().when(service).delete(existingId);


        // ========= ACT + ASSERT =========

        mockMvc.perform(delete("/products/{id}", existingId))

                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("DELETE /products/{id} deve retornar 404 quando ID não existir")
    void delete_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        doThrow(new ResourceNotFoundException("Resource not found"))
                .when(service).delete(nonExistingId);


        // ========= ACT + ASSERT =========

        mockMvc.perform(delete("/products/{id}", nonExistingId))

                .andExpect(status().isNotFound());
    }

}
