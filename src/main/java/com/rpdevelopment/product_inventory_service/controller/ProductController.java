package com.rpdevelopment.product_inventory_service.controller;

import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductCategoryStockDTO;
import com.rpdevelopment.product_inventory_service.dto.product.ProductStockDTO;
import com.rpdevelopment.product_inventory_service.dto.projection.ProductCategoryProjection;
import com.rpdevelopment.product_inventory_service.service.ProductService;
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
@Tag(name = "Products", description = "Controller for Product")
@RequestMapping(value = "/products")
public class ProductController {

    //========= DEPENDENCIAS ==============

    @Autowired
    private ProductService productService;


    //========= GET ==============

    //FIND ALL
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Lista todos os produtos",
            description = "Retorna uma lista paginada de produtos com suas informações.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    @GetMapping
    public ResponseEntity<Page<ProductCategoryDTO>> findall(Pageable pageable) {
        Page<ProductCategoryDTO> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    //FIND BY ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Busca produto por ID",
            description = "Retorna os dados de um produto com base no ID informado. Caso não seja encontrado, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductCategoryDTO> findById(@PathVariable Long id) {
        ProductCategoryDTO productId = productService.findById(id);
        return ResponseEntity.ok(productId);
    }

    //FIND BY SKU
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Busca produto por SKU",
            description = "Retorna os dados de um produto com base no código SKU informado. Caso não seja encontrado, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping(value = "/sku")
    public ResponseEntity<ProductCategoryDTO> findBySku(
            @RequestParam(name = "sku") String sku ) {
        ProductCategoryDTO productSku = productService.findAllBySku(sku);
        return ResponseEntity.ok(productSku);
    }

    //FIND ALL PRODUCT ACTIVE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Lista produtos ativos",
            description = "Retorna uma lista paginada de produtos com status ativo, incluindo suas informações.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    @GetMapping(value = "/active")
    public ResponseEntity<Page<ProductCategoryDTO>> findAllByActive(
            @RequestParam(name = "active", defaultValue = "true")
            boolean active, Pageable pageable) {
        Page<ProductCategoryDTO> productActive = productService.findAllProductActive(pageable, active);
        return ResponseEntity.ok(productActive);
    }

    //FIND ALL PRODUCTS BY CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/category")
    @Operation(
            summary = "Lista produtos por categoria",
            description = "Retorna uma lista paginada de produtos pela categoria, incluindo suas informações.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Page<ProductCategoryProjection>> findAllByCategory(
            @RequestParam(name = "category", defaultValue = "")
            String category, Pageable pageable){
        Page<ProductCategoryProjection> productByCategory = productService.findAllProductByCategory(category, pageable);
        return ResponseEntity.ok(productByCategory);
    }

    //FIND ALL PRODUCTS BY NAME (SEARCH)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(
            summary = "Busca produto por nome",
            description = "Retorna os dados de um produto com base no nome informado. Caso não seja encontrado, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @GetMapping(value = "/name")
    public ResponseEntity<Page<ProductCategoryDTO>> seachByName(
            @RequestParam(name = "name", defaultValue = "")
            String name, Pageable pageable) {
        Page<ProductCategoryDTO> searchByName = productService.searchByName(name, pageable);
        return ResponseEntity.ok(searchByName);
    }


    //========= POST ==============

    //NEW PRODUCT
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Cria um novo produto",
            description = "Cria um novo produto com suas informações, incluindo dados de estoque e categoria.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
            }
    )
    @PostMapping
    public ResponseEntity<ProductCategoryStockDTO> create(@Valid @RequestBody ProductCategoryStockDTO productCategoryStockDTO) {
        ProductCategoryStockDTO newProduct = productService.insert(productCategoryStockDTO);
        return ResponseEntity.ok(newProduct);
    }


    //========= UPDATE ===========

    // PUT PRODUCT AND CATEGORY
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Atualiza um produto por ID",
            description = "Atualiza os dados de um produto existente com base no ID informado, incluindo suas informações e categoria.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                    @ApiResponse(responseCode = "422", description = "Erro de validação dos dados")
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductCategoryDTO> update(@PathVariable Long id, @Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDTO updatedProduct = productService.update(productCategoryDTO, id);
        return ResponseEntity.ok(updatedProduct);
    }


    //========= DELETE ===========

    // DELETE PRODUCT AND STOCK ASSOCIATE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Remove produto por ID",
            description = "Remove um produto com base no ID informado, incluindo seu estoque associado. Caso o produto não seja encontrado, retorna status 404.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
