package com.naitik.car_dealership_api.service.impl;

import com.naitik.car_dealership_api.dto.request.RegisterRequest;
import com.naitik.car_dealership_api.entity.Role;
import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.exception.EmailAlreadyExistsException;
import com.naitik.car_dealership_api.repository.UserRepository;
import com.naitik.car_dealership_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}