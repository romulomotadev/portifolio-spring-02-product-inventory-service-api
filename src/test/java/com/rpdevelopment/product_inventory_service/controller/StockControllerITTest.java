package com.rpdevelopment.product_inventory_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.service.StockService;
import com.rpdevelopment.product_inventory_service.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rpdevelopment.product_inventory_service.factory.test.product.ProductStockFactory.createValidProductStockDTO;
import static com.rpdevelopment.product_inventory_service.factory.test.stock.StockFactory.createValidStockDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StockControllerITTest {

    // ========= DEPENDÊNCIAS =========

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockService stockService;

    @Autowired
    private TokenUtil tokenUtil;


    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;

    private ProductStockDTO productStockDTO;
    private StockDTO stockDTO;

    private Pageable pageable;
    private Page<ProductStockDTO> page;

    private String bearerToken;


    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 999L;

        productStockDTO = createValidProductStockDTO();
        stockDTO = createValidStockDTO();

        pageable = PageRequest.of(0, 10);

        page = new PageImpl<>(List.of(productStockDTO), pageable, 1);

        //Inicialização token user admin
        String username = "admin@gmail.com";
        String password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
    }


    // ========= FIND ALL =========

    @Test
    @DisplayName("GET /stocks deve retornar página de DTO")
    void findAll_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(stockService.findAll(any())).thenReturn(page);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/stocks")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(productStockDTO.getName()))
                .andExpect(jsonPath("$.content[0].description").value(productStockDTO.getDescription()))
                .andExpect(jsonPath("$.content[0].price").value(productStockDTO.getPrice()))
                .andExpect(jsonPath("$.content[0].stockDTO.quantity").value(productStockDTO.getStockDTO().getQuantity()))
                .andExpect(jsonPath("$.content[0].stockDTO.minimum_stock").value(productStockDTO.getStockDTO().getMinimum_stock()));
    }


    // ========= FIND BY ID =========

    @Test
    @DisplayName("GET /stocks/{id} deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(stockService.findById(existingId)).thenReturn(productStockDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/stocks/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(productStockDTO.getName()))
                .andExpect(jsonPath("$.description").value(productStockDTO.getDescription()))
                .andExpect(jsonPath("$.price").value(productStockDTO.getPrice()))
                .andExpect(jsonPath("$.stockDTO.quantity").value(productStockDTO.getStockDTO().getQuantity()))
                .andExpect(jsonPath("$.stockDTO.minimum_stock").value(productStockDTO.getStockDTO().getMinimum_stock()));
    }


    @Test
    @DisplayName("GET /stocks/{id} deve retornar 404 quando ID não existir")
    void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(stockService.findById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/stocks/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }


    // ========= FIND ALL PRODUCT BY STOCK LOW =========

    @Test
    @DisplayName("GET /stocks/low deve retornar página de DTO")
    void findAllStockLow_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(stockService.findAllStockLow(any())).thenReturn(page);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/stocks/low")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(productStockDTO.getName()))
                .andExpect(jsonPath("$.content[0].description").value(productStockDTO.getDescription()))
                .andExpect(jsonPath("$.content[0].price").value(productStockDTO.getPrice()))
                .andExpect(jsonPath("$.content[0].stockDTO.quantity").value(productStockDTO.getStockDTO().getQuantity()))
                .andExpect(jsonPath("$.content[0].stockDTO.minimum_stock").value(productStockDTO.getStockDTO().getMinimum_stock()));
    }


    // ========= FIND ALL PRODUCTS OUT OF STOCK =========

    @Test
    @DisplayName("GET /stocks/out-of-stock deve retornar página de DTO")
    void findAllOutOfStock_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(stockService.findAllOutOfStock(any())).thenReturn(page);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/stocks/out-of-stock")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId))
                .andExpect(jsonPath("$.content[0].name").value(productStockDTO.getName()))
                .andExpect(jsonPath("$.content[0].description").value(productStockDTO.getDescription()))
                .andExpect(jsonPath("$.content[0].price").value(productStockDTO.getPrice()))
                .andExpect(jsonPath("$.content[0].stockDTO.quantity").value(productStockDTO.getStockDTO().getQuantity()))
                .andExpect(jsonPath("$.content[0].stockDTO.minimum_stock").value(productStockDTO.getStockDTO().getMinimum_stock()));
    }


    // ========= UPDATE =========

    @Test
    @DisplayName("PUT /stocks/{id} deve atualizar e retornar DTO")
    void update_ShouldReturnUpdatedDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(stockService.update(eq(existingId), any(StockDTO.class))).thenReturn(stockDTO);


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/stocks/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.minimum_stock").value(stockDTO.getMinimum_stock()))
                .andExpect(jsonPath("$.quantity").value(stockDTO.getQuantity()));
    }


    @Test
    @DisplayName("PUT /stocks/{id} deve retornar 404 quando ID não existir")
    void update_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(stockService.update(eq(nonExistingId), any(StockDTO.class)))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/stocks/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDTO)))

                .andExpect(status().isNotFound());
    }


    // ========= VALIDATION =========

    @Test
    @DisplayName("Create deve retornar 400 quando quantidade estoque menor que zero")
    public void createShouldReturn400whenStockQuantityIsLessThanZero() throws Exception {

        StockDTO invalidDTO = stockDTO;
        invalidDTO.setQuantity(-1);

        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/stocks/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }


    @Test
    @DisplayName("Create deve retornar 400 quando quantidade minima menor que zero")
    public void createShouldReturn400WhenTheMinimumQuantityIsLessThanZero() throws Exception {

        StockDTO invalidDTO = stockDTO;
        invalidDTO.setMinimum_stock(-1);

        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/stocks/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }

}
