package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.dto.category.CategoryDTO;
import com.rpdevelopment.product_inventory_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Categories", description = "Controller for Categories")
@RequestMapping(value = "/categories")
public class CategoryController {

    //============ DEPENDENCIAS ===============

    @Autowired
    private CategoryService service;


    //============ GET ===============

    //FIND ALL
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Lista todas as categorias",
            description = "Retorna uma lista paginada de categorias.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    //FIND BY ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Busca categoria por ID",
            description = "Retorna os dados de uma categoria com base no ID informado. Caso não seja encontrada, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    // ========= POST ==========

    // NEW CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Cria uma nova categoria",
            description = "Cria uma nova categoria e retorna seus dados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "409", description = "Conflito: categoria já existente"),
                    @ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
            }
    )
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }


    // ========= UPDATE ==========

    // UPDATE CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Atualiza categoria por ID",
            description = "Atualiza uma categoria existente com base no ID informado e retorna seus dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "409", description = "Conflito: categoria já existente"),
                    @ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }


    // ========= DELETE ==========

    // DELETE CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Remove categoria por ID",
            description = "Remove uma categoria com base no ID informado. Caso não seja encontrada, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
            }
    )
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
