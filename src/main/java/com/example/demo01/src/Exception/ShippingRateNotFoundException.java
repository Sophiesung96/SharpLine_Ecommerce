package com.example.demo01.src.Exception;

public class ShippingRateNotFoundException extends RuntimeException {

    public ShippingRateNotFoundException() {
        super();
    }

    public ShippingRateNotFoundException(String message) {
        super(message);
    }

    public ShippingRateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShippingRateNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ShippingRateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
