package com.sda.library.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class AppError {

    private HttpStatus status;
    private String massage;
    private List<String> errors;

    public AppError(HttpStatus status, String massage, List<String> errors) {
        this.status = status;
        this.massage = massage;
        this.errors = errors;
    }
}
