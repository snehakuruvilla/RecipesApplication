package com.sk.recipe.exception;

public class NoItemFoundException extends Exception{
    public NoItemFoundException() {
    }

    public NoItemFoundException(String message) {
        super(message);
    }

    public NoItemFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoItemFoundException(Throwable cause) {
        super(cause);
    }

    public NoItemFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
