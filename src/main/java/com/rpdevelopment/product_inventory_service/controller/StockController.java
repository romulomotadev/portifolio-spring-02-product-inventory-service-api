package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.dto.stock.StockDTO;
import com.rpdevelopment.product_inventory_service.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Stocks", description = "Controller for Stock")
@RequestMapping(value = "/stocks")
public class StockController {

    // ======= DEPENDENCIAS ===========

    @Autowired
    private StockService productStockService;
    @Autowired
    private StockService stockService;


    // ========== GET ==============

    //FIND ALL
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Lista produtos em estoque com sua quantidade, e quantidade minima.",
            description = "Retorna uma lista de todos os produtos com sua quantidade, e estoque mínimo de forma paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
            })
    @GetMapping
    public ResponseEntity<Page<ProductStockDTO>> findAll(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAll(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }

    //FIND ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Retorna um produto com sua quantidade, e quantidade minima pelo seu ID",
            description = "Retorna um produto e sua quantidade no estoque, com base no ID informado. Caso o produto não seja encontrado, será retornado status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductStockDTO> findById(@PathVariable Long id) {
        ProductStockDTO productStockDTO = productStockService.findById(id);
        return ResponseEntity.ok(productStockDTO);
    }

    //FIND ALL PRODUCT BY STOCK LOW
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Lista produtos com estoque abaixo do mínimo",
            description = "Retorna uma lista paginada de produtos cuja quantidade em estoque está abaixo do limite mínimo definido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    @GetMapping(value = "/low")
    public ResponseEntity<Page<ProductStockDTO>> findAllStockLow(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAllStockLow(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }

    //FIND ALL PRODUCTS OUT OF STOCK
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Lista produtos sem unidade no estoque.",
            description = "Retorna todos os produtos sem nenhuma unidade no estoque de forma paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
            })
    @GetMapping(value = "/out-of-stock")
    public ResponseEntity<Page<ProductStockDTO>> findAllOutOfStock(Pageable pageable) {
        Page<ProductStockDTO> productStockDTOs = productStockService.findAllOutOfStock(pageable);
        return ResponseEntity.ok(productStockDTOs);
    }


    // ========== UPDATE ==============

    // UPDATE STOCK BY PRODUCT
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Atualiza o estoque de um produto",
            description = "Atualiza a quantidade disponível e o limite mínimo de estoque (threshold) de um produto existente pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                    @ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @Valid @RequestBody StockDTO  stockDTO) {
        stockDTO = stockService.update(id, stockDTO);
        return ResponseEntity.ok(stockDTO);
    }

}
