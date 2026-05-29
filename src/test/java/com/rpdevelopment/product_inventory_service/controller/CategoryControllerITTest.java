package com.rpdevelopment.product_inventory_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import com.rpdevelopment.product_inventory_service.service.CategoryService;
import com.rpdevelopment.product_inventory_service.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rpdevelopment.product_inventory_service.factory.test.category.CategoryFactory.createValidCategoryDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerITTest {

    // ========= DEPENDÊNCIAS =========

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService service;

    @Autowired
    private TokenUtil tokenUtil;


    // ========= DADOS BASE =========

    private Long existingId;
    private Long nonExistingId;

    private CategoryDTO dto;
    private String bearerToken;



    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 999L;

        dto = createValidCategoryDTO();

        //Inicialização token user admin
        String username = "admin@gmail.com";
        String password = "123456";

        bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);

    }

    // ========= FIND ALL =========

    @Test
    @DisplayName("GET /categories deve retornar página de DTO")
    void findAll_ShouldReturnPage() throws Exception {

        // ========= ARRANGE =========

        when(service.findAll()).thenReturn(List.of(dto));


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/categories")
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(existingId))
                .andExpect(jsonPath("$[0].name").value(dto.getName()));
    }


    // ========= FIND BY ID =========

    @Test
    @DisplayName("GET /categories/{id} deve retornar DTO quando ID existir")
    void findById_ShouldReturnDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(service.findById(existingId)).thenReturn(dto);


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/categories/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }


    @Test
    @DisplayName("GET /categories/{id} deve retornar 404 quando ID não existir")
    void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(service.findById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(get("/categories/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }


    // ========= CREATE =========

    @Test
    @DisplayName("POST /categories deve retornar DTO criado")
    void create_ShouldReturnCreatedDTO() throws Exception {

        // ========= ARRANGE =========

        when(service.save(any(CategoryDTO.class))).thenReturn(dto);


        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/categories")
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }


    // ========= UPDATE =========

    @Test
    @DisplayName("PUT /categories/{id} deve atualizar e retornar DTO")
    void update_ShouldReturnUpdatedDTO_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        when(service.update(existingId, dto)).thenReturn(dto);


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/categories/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }


    @Test
    @DisplayName("PUT /categories/{id} deve retornar 404 quando ID não existir")
    void update_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        when(service.update(nonExistingId, dto))
                .thenThrow(new ResourceNotFoundException("Resource not found"));


        // ========= ACT + ASSERT =========

        mockMvc.perform(put("/categories/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                .andExpect(status().isNotFound());
    }


    // ========= DELETE =========

    @Test
    @DisplayName("DELETE /categories/{id} deve retornar 204 quando ID existir")
    void delete_ShouldReturnNoContent_WhenIdExists() throws Exception {

        // ========= ARRANGE =========

        doNothing().when(service).delete(existingId);


        // ========= ACT + ASSERT =========

        mockMvc.perform(delete("/categories/{id}", existingId)
                        .header("Authorization", "Bearer " + bearerToken))

                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("DELETE /categories/{id} deve retornar 404 quando ID não existir")
    void delete_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {

        // ========= ARRANGE =========

        doThrow(new ResourceNotFoundException("Resource not found"))
                .when(service).delete(nonExistingId);


        // ========= ACT + ASSERT =========

        mockMvc.perform(delete("/categories/{id}", nonExistingId)
                        .header("Authorization", "Bearer " + bearerToken))

                .andExpect(status().isNotFound());
    }


    // ========= VALIDATION =========

    @Test
    @DisplayName("Create deve retornar 400 quando name for inválido")
    public void createShouldReturn400WhenNameIsinValid() throws Exception {

        CategoryDTO invalidDTO = dto;
        invalidDTO.setName(null);

        // ========= ACT + ASSERT =========

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());

        //VERIFICAÇÃO - service não chamado
        Mockito.verify(service, Mockito.never()).save(Mockito.any());
    }

}
