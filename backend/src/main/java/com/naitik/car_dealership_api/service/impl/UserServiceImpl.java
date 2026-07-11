package com.naitik.car_dealership_api.service.impl;

import com.naitik.car_dealership_api.dto.request.LoginRequest;
import com.naitik.car_dealership_api.dto.request.RegisterRequest;
import com.naitik.car_dealership_api.dto.response.LoginResponse;
import com.naitik.car_dealership_api.entity.Role;
import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.exception.EmailAlreadyExistsException;
import com.naitik.car_dealership_api.exception.InvalidCredentialsException;
import com.naitik.car_dealership_api.repository.UserRepository;
import com.naitik.car_dealership_api.service.JwtService;
import com.naitik.car_dealership_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return LoginResponse.builder().build();
    }
}