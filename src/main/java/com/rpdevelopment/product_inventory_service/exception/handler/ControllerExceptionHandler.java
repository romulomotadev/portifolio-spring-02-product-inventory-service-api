package com.rpdevelopment.product_inventory_service.exception.handler;

import com.rpdevelopment.product_inventory_service.dto.error.CustomErrorDto;
import com.rpdevelopment.product_inventory_service.dto.error.ValidateErrorDto;
import com.rpdevelopment.product_inventory_service.exception.exceptions.DuplicateResourceException;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ForbiddenException;
import com.rpdevelopment.product_inventory_service.exception.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    // TRATANDO ID NÃO ENCONTRADO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDto> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDto err = new CustomErrorDto(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    // TRATANDO DADOS INVÁLIDOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDto> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidateErrorDto err = new ValidateErrorDto(
                Instant.now(),
                status.value(),
                "Argument Not Valid",
                request.getRequestURI()
        );
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    // TRATANDO INTEGRIDADE DO BANCO
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorDto> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDto err = new CustomErrorDto(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    //TRATANDO DADOS DUPLICADOS
    @ExceptionHandler(DuplicateResourceException.class)
    private ResponseEntity<CustomErrorDto> duplicateResourceException(DuplicateResourceException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDto err = new CustomErrorDto(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    //TRATANDO AUTORIZAÇÃO
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorDto> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
