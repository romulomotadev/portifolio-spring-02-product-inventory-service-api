package com.rpdevelopment.product_inventory_service.exception.exceptions;

public class DuplicateResourceException extends RuntimeException {
  public DuplicateResourceException(String message) {
    super(message);
  }
}
