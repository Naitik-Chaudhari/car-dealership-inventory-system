package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.entity.User;

public interface JwtService {

    String generateToken(User user);

}