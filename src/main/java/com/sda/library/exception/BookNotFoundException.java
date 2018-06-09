package com.sda.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Nie mogę znaleźć książki");
    }
}
