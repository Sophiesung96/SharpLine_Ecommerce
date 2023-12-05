package com.example.demo01.src.Exception;

public class PayPalApiException extends RuntimeException{
    public PayPalApiException(String message) {
        super(message);
    }

    public PayPalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    protected PayPalApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
