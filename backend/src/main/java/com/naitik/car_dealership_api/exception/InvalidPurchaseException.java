package com.naitik.car_dealership_api.exception;

public class InvalidPurchaseException extends RuntimeException {

    public InvalidPurchaseException(String message) {
        super(message);
    }
}