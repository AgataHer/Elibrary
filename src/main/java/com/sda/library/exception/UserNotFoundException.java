package com.sda.library.exception;

import com.sda.library.model.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (Long id) {super("Nie mogę znaleźć użytkownika");}
}
