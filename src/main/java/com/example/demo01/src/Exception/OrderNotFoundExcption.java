package com.example.demo01.src.Exception;

public class OrderNotFoundExcption extends RuntimeException {

    public OrderNotFoundExcption() {
        super();
    }

    public OrderNotFoundExcption(String message) {
        super(message);
    }

    public OrderNotFoundExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundExcption(Throwable cause) {
        super(cause);
    }

    protected OrderNotFoundExcption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
