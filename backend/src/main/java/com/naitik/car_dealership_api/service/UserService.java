package com.naitik.car_dealership_api.service;


import com.naitik.car_dealership_api.dto.request.LoginRequest;
import com.naitik.car_dealership_api.dto.request.RegisterRequest;
import com.naitik.car_dealership_api.dto.response.LoginResponse;

public interface UserService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}