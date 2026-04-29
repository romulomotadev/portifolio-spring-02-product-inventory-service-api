package com.rpdevelopment.product_inventory_service.dto.category;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rpdevelopment.product_inventory_service.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Representa uma categoria do sistema.")
@JsonPropertyOrder({ "id", "name" })
public class CategoryDTO {

    //========== ATRIBUTOS ==============

    @Schema(description = "ID da categoria gerado automaticamente", example = "1")
    private Long id;

    @Schema(description = "Nome da categoria (obrigatório)", example = "Componentes")
    @NotBlank(message = "Nome da categoria é obrigatório")
    private String name;


    //========== CONSTRUTORES ==============

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }


    //========== CONSTRUTORES ==============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
