package com.edu.ulab.app.exception;

public class CantAccessException extends RuntimeException{

    public CantAccessException(String message) {
        super(message);
    }
}
