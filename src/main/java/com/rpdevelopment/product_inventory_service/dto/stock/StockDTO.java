package com.rpdevelopment.product_inventory_service.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpdevelopment.product_inventory_service.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public class StockDTO {

    //======== ATRIBUTOS =============

    @Schema(description = "ID do estoque gerado automaticamente", example = "1")
    private Long id;

    @Schema(description = "Quantidade disponível em estoque", example = "100")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantity;

    @Schema(description = "Quantidade mínima para alerta de reposição", example = "10")
    @Positive(message = "O estoque mínimo deve ser maior que zero")
    private Integer minimum_stock;


    //======== ATRIBUTOS RELACIONADOS =============

    @JsonIgnore
    private Product product;


    //======== CONSTRUTORES =============

    public StockDTO() {
    }

    public StockDTO(Long id, Integer quantity, Integer minimum_stock, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.minimum_stock = minimum_stock;
        this.product = product;
    }

    public StockDTO(com.rpdevelopment.product_inventory_service.entity.StockDTO entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.minimum_stock = entity.getMinimum_stock();
        this.product = entity.getProduct();
    }


//======== GETTER | SETTER =============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimum_stock() {
        return minimum_stock;
    }

    public void setMinimum_stock(Integer minimum_stock) {
        this.minimum_stock = minimum_stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
