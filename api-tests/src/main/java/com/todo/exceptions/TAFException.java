package com.todo.exceptions;

public class TAFException extends RuntimeException {

    public TAFException(String message) {
        super(message);
    }

    public TAFException(Throwable cause) {
        super(cause);
    }
}
