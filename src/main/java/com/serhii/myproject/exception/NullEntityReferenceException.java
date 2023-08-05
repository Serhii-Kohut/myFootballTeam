package com.serhii.myproject.exception;

public class NullEntityReferenceException extends RuntimeException{
    public NullEntityReferenceException(String message) {
        super(message);
    }
}
