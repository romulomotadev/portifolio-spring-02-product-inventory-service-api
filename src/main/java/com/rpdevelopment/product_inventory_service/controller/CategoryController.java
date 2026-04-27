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
            summary = "Buscar todas categorias.",
            description = "Retorna todas as categorias de forma paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido"),
            })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    //FIND BY ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria com base no ID informado. Caso o usuário não seja encontrado, será retornado status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    // ========= POST ==========

    // NEW CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Criar nova categoria",
            description = "Cria uma nova categoria e retorna seus dados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Novo categoria criado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido"),
                    @ApiResponse(responseCode = "409", description = "Conflito de dados: dados já existentes no banco de dados"),
                    @ApiResponse(responseCode = "422", description = "Dados inválidos"),
            })
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }


    // ========= UPDATE ==========

    // UPDATE CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Atualizar categoria por ID",
            description = "Atualiza uma categoria existente no banco de dados e retorna seus dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido"),
                    @ApiResponse(responseCode = "409", description = "Conflito de dados: dados já existentes no banco de dados"),
                    @ApiResponse(responseCode = "422", description = "Dados inválidos"),
            })
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }


    // ========= DELETE ==========

    // DELETE CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Excluir categoria por ID",
            description = "Exclui uma categoriad com base no ID informado. Caso a categoria não seja encontrado, será retornado status 404.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            })
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
