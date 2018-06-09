package com.sda.library.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Nie mogę znaleźć kategorii");
    }
}
