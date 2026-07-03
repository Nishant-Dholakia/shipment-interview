package com.example.java.exception;

import org.springframework.validation.Errors;

public class UsersAlreadyConnectedException extends RuntimeException {
    public UsersAlreadyConnectedException(String message) {
        super(message);
    }

}
