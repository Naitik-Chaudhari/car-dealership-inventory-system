package com.naitik.car_dealership_api.service;


import com.naitik.car_dealership_api.dto.request.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);

}