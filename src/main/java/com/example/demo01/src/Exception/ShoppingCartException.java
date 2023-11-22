package com.example.demo01.src.Exception;

public class ShoppingCartException extends RuntimeException {
    public ShoppingCartException(String message) {
        super(message);
    }

    public ShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ShoppingCartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
