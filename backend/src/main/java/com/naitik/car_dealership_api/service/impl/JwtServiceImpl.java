package com.naitik.car_dealership_api.service.impl;

import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}