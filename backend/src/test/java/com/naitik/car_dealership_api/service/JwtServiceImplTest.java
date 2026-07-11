package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.entity.Role;
import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        jwtService = new JwtServiceImpl(
                "ThisIsMyVerySecureSecretKeyForJwtGeneration123456789",
                86400000L
        );
    }

    @Test
    void shouldGenerateValidJwtToken() {

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUsernameFromToken() {

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        String username = jwtService.extractUsername(token);

        assertEquals(user.getEmail(), username);
    }

    @Test
    void shouldExtractRoleFromToken() {

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .role(Role.ADMIN)
                .build();

        String token = jwtService.generateToken(user);

        String role = jwtService.extractRole(token);

        assertEquals("ADMIN", role);
    }

    @Test
    void shouldValidateJwtToken() {

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void shouldReturnFalseWhenTokenIsExpired() {

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        assertFalse(jwtService.isTokenExpired(token));
    }
}