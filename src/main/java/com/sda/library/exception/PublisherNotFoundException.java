package com.sda.library.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(Long id) {
        super("Nie mogę znaleźć wydawcy");
    }
}
