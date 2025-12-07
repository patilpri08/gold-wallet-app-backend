package com.goldrental.exception;

public class JewelleryNotFoundException extends RuntimeException {
    public JewelleryNotFoundException(Long id) {
        super("Jewellery item not found with id: " + id);
    }
}