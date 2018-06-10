package com.sda.library.exception;

import com.sda.library.model.User;

public class ListOfHiresNotFoundException extends RuntimeException {
    public ListOfHiresNotFoundException(Long id){super("Lista wypożyczeń jest pusta");}
}
