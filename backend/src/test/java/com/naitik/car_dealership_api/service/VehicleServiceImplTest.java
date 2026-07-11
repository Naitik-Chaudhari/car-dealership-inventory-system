package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import com.naitik.car_dealership_api.exception.DuplicateVehicleException;
import com.naitik.car_dealership_api.exception.VehicleNotFoundException;
import com.naitik.car_dealership_api.repository.VehicleRepository;
import com.naitik.car_dealership_api.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @Test
    void shouldReturnAllVehicles() {

        // Arrange
        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build(),

                Vehicle.builder()
                        .id(2L)
                        .make("Honda")
                        .model("City")
                        .category(VehicleCategory.SEDAN)
                        .price(BigDecimal.valueOf(1500000))
                        .quantity(10)
                        .build()
        );

        when(vehicleRepository.findAll())
                .thenReturn(vehicles);

        // Act
        List<VehicleResponse> response =
                vehicleService.getAllVehicles();

        // Assert
        assertEquals(2, response.size());

        assertEquals("Toyota", response.get(0).getMake());
        assertEquals("Honda", response.get(1).getMake());

        verify(vehicleRepository).findAll();
    }

    @Test
    void shouldSearchVehiclesByMake() {

        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build()
        );

        when(vehicleRepository.findAll(any(Specification.class)))
                .thenReturn(vehicles);

        List<VehicleResponse> response =
                vehicleService.searchVehicles(
                        "Toyota",
                        null,
                        null,
                        null,
                        null
                );

        assertEquals(1, response.size());
        assertEquals("Toyota", response.getFirst().getMake());

        verify(vehicleRepository).findAll(any(Specification.class));
    }

    @Test
    void shouldSearchVehiclesByModel() {

        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build()
        );

        when(vehicleRepository.findAll(any(Specification.class)))
                .thenReturn(vehicles);

        List<VehicleResponse> response = vehicleService.searchVehicles(
                null,
                "Fortuner",
                null,
                null,
                null
        );

        assertEquals(1, response.size());
        assertEquals("Fortuner", response.getFirst().getModel());

        verify(vehicleRepository).findAll(any(Specification.class));
    }

    @Test
    void shouldSearchVehiclesByCategory() {

        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build()
        );

        when(vehicleRepository.findAll(any(Specification.class)))
                .thenReturn(vehicles);

        List<VehicleResponse> response = vehicleService.searchVehicles(
                null,
                null,
                VehicleCategory.SUV,
                null,
                null
        );

        assertEquals(1, response.size());
        assertEquals(VehicleCategory.SUV, response.getFirst().getCategory());

        verify(vehicleRepository).findAll(any(Specification.class));
    }

    @Test
    void shouldSearchVehiclesByPriceRange() {

        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build()
        );

        when(vehicleRepository.findAll(any(Specification.class)))
                .thenReturn(vehicles);

        List<VehicleResponse> response = vehicleService.searchVehicles(
                null,
                null,
                null,
                BigDecimal.valueOf(4000000),
                BigDecimal.valueOf(5000000)
        );

        assertEquals(1, response.size());
        assertEquals(BigDecimal.valueOf(4500000), response.getFirst().getPrice());

        verify(vehicleRepository).findAll(any(Specification.class));
    }

    @Test
    void shouldSearchVehiclesUsingMultipleFilters() {

        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .id(1L)
                        .make("Toyota")
                        .model("Fortuner")
                        .category(VehicleCategory.SUV)
                        .price(BigDecimal.valueOf(4500000))
                        .quantity(5)
                        .build()
        );

        when(vehicleRepository.findAll(any(Specification.class)))
                .thenReturn(vehicles);

        List<VehicleResponse> response = vehicleService.searchVehicles(
                "Toyota",
                "Fortuner",
                VehicleCategory.SUV,
                BigDecimal.valueOf(4000000),
                BigDecimal.valueOf(5000000)
        );

        assertEquals(1, response.size());

        VehicleResponse vehicle = response.getFirst();

        assertEquals("Toyota", vehicle.getMake());
        assertEquals("Fortuner", vehicle.getModel());
        assertEquals(VehicleCategory.SUV, vehicle.getCategory());
        assertEquals(BigDecimal.valueOf(4500000), vehicle.getPrice());

        verify(vehicleRepository).findAll(any(Specification.class));
    }

    @Test
    void shouldUpdateVehicleSuccessfully() {

        Long vehicleId = 1L;

        Vehicle existingVehicle = Vehicle.builder()
                .id(vehicleId)
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        VehicleRequest request = VehicleRequest.builder()
                .make("Honda")
                .model("City")
                .category(VehicleCategory.SEDAN)
                .price(BigDecimal.valueOf(1800000))
                .quantity(8)
                .build();

        when(vehicleRepository.findById(vehicleId))
                .thenReturn(Optional.of(existingVehicle));

        when(vehicleRepository.existsByMakeAndModelAndCategory(
                "Honda",
                "City",
                VehicleCategory.SEDAN))
                .thenReturn(false);

        when(vehicleRepository.save(any(Vehicle.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        VehicleResponse response =
                vehicleService.updateVehicle(vehicleId, request);

        assertEquals("Honda", response.getMake());
        assertEquals("City", response.getModel());
        assertEquals(VehicleCategory.SEDAN, response.getCategory());

        verify(vehicleRepository).save(existingVehicle);
    }

    @Test
    void shouldThrowExceptionWhenVehicleDoesNotExist() {

        VehicleRequest request = VehicleRequest.builder()
                .make("Honda")
                .model("City")
                .category(VehicleCategory.SEDAN)
                .price(BigDecimal.valueOf(1800000))
                .quantity(8)
                .build();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.updateVehicle(1L, request)
        );

        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToDuplicateVehicle() {

        Vehicle existingVehicle = Vehicle.builder()
                .id(1L)
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        VehicleRequest request = VehicleRequest.builder()
                .make("Honda")
                .model("City")
                .category(VehicleCategory.SEDAN)
                .price(BigDecimal.valueOf(1800000))
                .quantity(8)
                .build();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(existingVehicle));

        when(vehicleRepository.existsByMakeAndModelAndCategory(
                "Honda",
                "City",
                VehicleCategory.SEDAN))
                .thenReturn(true);

        assertThrows(
                DuplicateVehicleException.class,
                () -> vehicleService.updateVehicle(1L, request)
        );

        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void shouldDeleteVehicleSuccessfully() {

        Vehicle vehicle = Vehicle.builder()
                .id(1L)
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingVehicle() {

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.deleteVehicle(1L)
        );

        verify(vehicleRepository, never()).deleteById(anyLong());
    }
}