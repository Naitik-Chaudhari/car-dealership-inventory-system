package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import com.naitik.car_dealership_api.exception.DuplicateVehicleException;
import com.naitik.car_dealership_api.repository.VehicleRepository;
import com.naitik.car_dealership_api.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void shouldAddVehicleSuccessfully() {

        // Arrange
        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        Vehicle savedVehicle = Vehicle.builder()
                .id(1L)
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        when(vehicleRepository.save(any(Vehicle.class)))
                .thenReturn(savedVehicle);

        // Act
        VehicleResponse response = vehicleService.addVehicle(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Toyota", response.getMake());
        assertEquals("Fortuner", response.getModel());
        assertEquals(VehicleCategory.SUV, response.getCategory());
        assertEquals(BigDecimal.valueOf(4500000), response.getPrice());
        assertEquals(5, response.getQuantity());

        ArgumentCaptor<Vehicle> captor =
                ArgumentCaptor.forClass(Vehicle.class);

        verify(vehicleRepository).save(captor.capture());

        Vehicle vehicle = captor.getValue();

        assertEquals(request.getMake(), vehicle.getMake());
        assertEquals(request.getModel(), vehicle.getModel());
        assertEquals(request.getCategory(), vehicle.getCategory());
        assertEquals(request.getPrice(), vehicle.getPrice());
        assertEquals(request.getQuantity(), vehicle.getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenVehicleAlreadyExists() {

        // Arrange
        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        when(vehicleRepository.existsByMakeAndModelAndCategory(
                request.getMake(),
                request.getModel(),
                request.getCategory()))
                .thenReturn(true);

        // Act & Assert
        DuplicateVehicleException exception = assertThrows(
                DuplicateVehicleException.class,
                () -> vehicleService.addVehicle(request)
        );

        assertEquals("Vehicle already exists", exception.getMessage());

        verify(vehicleRepository)
                .existsByMakeAndModelAndCategory(
                        request.getMake(),
                        request.getModel(),
                        request.getCategory());

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}