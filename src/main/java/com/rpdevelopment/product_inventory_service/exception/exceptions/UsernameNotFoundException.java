package com.rpdevelopment.product_inventory_service.exception.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
