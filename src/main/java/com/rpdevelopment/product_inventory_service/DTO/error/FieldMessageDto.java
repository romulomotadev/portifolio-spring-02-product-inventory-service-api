package com.rpdevelopment.product_inventory_service.DTO.error;

public class FieldMessageDto {

    //ATRIBUTOS
    private String fieldName;
    private String message;

    //CONSTRUTORES
    public FieldMessageDto() {
    }

    public FieldMessageDto(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    //GETTER
    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
