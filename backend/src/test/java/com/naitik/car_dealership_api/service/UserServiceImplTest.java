package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.dto.request.LoginRequest;
import com.naitik.car_dealership_api.dto.request.RegisterRequest;
import com.naitik.car_dealership_api.dto.response.LoginResponse;
import com.naitik.car_dealership_api.entity.Role;
import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.exception.EmailAlreadyExistsException;
import com.naitik.car_dealership_api.repository.UserRepository;
import com.naitik.car_dealership_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUserSuccessfully() {

        // Arrange
        RegisterRequest request = RegisterRequest.builder()
                .name("Naitik")
                .email("naitik@gmail.com")
                .password("password123")
                .build();

        // Act
        userService.register(request);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("Naitik", savedUser.getName());
        assertEquals("naitik@gmail.com", savedUser.getEmail());
        assertEquals("password123", savedUser.getPassword());
        assertEquals(Role.USER, savedUser.getRole());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        // Arrange
        RegisterRequest request = RegisterRequest.builder()
                .name("Naitik")
                .email("naitik@gmail.com")
                .password("password123")
                .build();

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        // Act & Assert
        EmailAlreadyExistsException exception =
                assertThrows(
                        EmailAlreadyExistsException.class,
                        () -> userService.register(request)
                );

        assertEquals("Email already registered.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldEncodePasswordBeforeSaving() {

        // Arrange
        RegisterRequest request = RegisterRequest.builder()
                .name("Naitik")
                .email("naitik@gmail.com")
                .password("password123")
                .build();

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword123");

        // Act
        userService.register(request);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("encodedPassword123", savedUser.getPassword());

        verify(passwordEncoder).encode("password123");
    }

    @Test
    void shouldLoginSuccessfully() {

        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("naitik@gmail.com")
                .password("password123")
                .build();

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()))
                .thenReturn(true);

        LoginResponse response = userService.login(request);

        assertNotNull(response);
    }

    @Test
    void shouldGenerateJwtTokenAfterSuccessfulLogin() {

        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("naitik@gmail.com")
                .password("password123")
                .build();

        User user = User.builder()
                .id(1L)
                .name("Naitik")
                .email("naitik@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        // Act
        LoginResponse response = userService.login(request);

        // Assert
        assertEquals("jwt-token", response.getToken());

        verify(jwtService).generateToken(user);
    }
}