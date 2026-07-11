package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    String extractRole(String token);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);

}