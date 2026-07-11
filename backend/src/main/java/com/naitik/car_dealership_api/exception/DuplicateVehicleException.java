package com.naitik.car_dealership_api.exception;

public class DuplicateVehicleException extends RuntimeException {

    public DuplicateVehicleException(String message) {
        super(message);
    }
}