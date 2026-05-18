package com.rpdevelopment.product_inventory_service.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpdevelopment.product_inventory_service.controller.ProductController;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductCategoryStockFactory.createProductCategoryStockValidFactoryDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductControllerValidationTest {

    // ========= DEPENDÊNCIAS =========

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService service;


    // ========= DADOS BASE =========

    private ProductCategoryStockDTO categoryStockDTO;


    @BeforeEach
    void setUp() {

        categoryStockDTO = createProductCategoryStockValidFactoryDTO();

    }


    // ========= VALIDAÇÕES =========

    @Test
    @DisplayName("Create deve retornar 400 quando name for inválido")
    public void createShouldReturn400WhenNameIsinValid() throws Exception {

        ProductCategoryStockDTO invalidDTO = categoryStockDTO;
        invalidDTO.setName(null);

        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }


    @Test
    @DisplayName("Create deve retornar 400 quando sku for inválido")
    public void createShouldReturn400WhenSkuIsinValid() throws Exception {

        ProductCategoryStockDTO invalidDTO = categoryStockDTO;
        invalidDTO.setSku(null);

        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }


    @Test
    @DisplayName("Create deve retornar 400 quando preço for inválido")
    public void createShouldReturn400WhenPriceIsinValid() throws Exception {

        ProductCategoryStockDTO invalidDTO = categoryStockDTO;
        invalidDTO.setPrice(null);

        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }


    @Test
    @DisplayName("Create deve retornar 400 quando preço menor que zero")
    public void createShouldReturn400whenPriceIsLessThanZero() throws Exception {

        ProductCategoryStockDTO invalidDTO = categoryStockDTO;
        invalidDTO.setPrice(-1.00);

        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }

}
