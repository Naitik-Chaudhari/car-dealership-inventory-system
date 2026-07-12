package com.naitik.car_dealership_api.exception;

public class InvalidRestockException extends RuntimeException {

    public InvalidRestockException(String message) {
        super(message);
    }
}