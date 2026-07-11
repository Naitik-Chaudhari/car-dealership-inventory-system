package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.dto.request.RegisterRequest;
import com.naitik.car_dealership_api.entity.Role;
import com.naitik.car_dealership_api.entity.User;
import com.naitik.car_dealership_api.repository.UserRepository;
import com.naitik.car_dealership_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

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
}