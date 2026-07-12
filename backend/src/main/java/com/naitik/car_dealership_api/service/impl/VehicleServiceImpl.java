package com.naitik.car_dealership_api.service.impl;

import com.naitik.car_dealership_api.dto.request.PurchaseRequest;
import com.naitik.car_dealership_api.dto.request.RestockRequest;
import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import com.naitik.car_dealership_api.exception.*;
import com.naitik.car_dealership_api.repository.VehicleRepository;
import com.naitik.car_dealership_api.service.VehicleService;
import com.naitik.car_dealership_api.specification.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private VehicleResponse mapToResponse(Vehicle vehicle) {

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .category(vehicle.getCategory())
                .price(vehicle.getPrice())
                .quantity(vehicle.getQuantity())
                .build();
    }

    private Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new VehicleNotFoundException("Vehicle not found"));
    }

    private void validatePurchaseQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidPurchaseException("Purchase quantity must be greater than zero");
        }
    }

    private void validateAvailableStock(Vehicle vehicle, Integer quantity) {
        if (vehicle.getQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock available");
        }
    }

    private void validateRestockQuantity(Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new InvalidRestockException(
                    "Restock quantity must be greater than zero");
        }
    }

    @Override
    public VehicleResponse addVehicle(VehicleRequest request) {

        Vehicle vehicle = Vehicle.builder()
                .make(request.getMake())
                .model(request.getModel())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        if (vehicleRepository.existsByMakeAndModelAndCategory(
                request.getMake(),
                request.getModel(),
                request.getCategory())) {

            throw new DuplicateVehicleException("Vehicle already exists");
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return mapToResponse(savedVehicle);
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {

        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<VehicleResponse> searchVehicles(
            String make,
            String model,
            VehicleCategory category,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return vehicleRepository.findAll(
                        VehicleSpecification.search(
                                make,
                                model,
                                category,
                                minPrice,
                                maxPrice))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new VehicleNotFoundException("Vehicle not found"));

        boolean duplicateVehicle =
                vehicleRepository.existsByMakeAndModelAndCategory(
                        request.getMake(),
                        request.getModel(),
                        request.getCategory());

        boolean isUpdatingToDifferentVehicle =
                !vehicle.getMake().equals(request.getMake()) ||
                        !vehicle.getModel().equals(request.getModel()) ||
                        vehicle.getCategory() != request.getCategory();

        if (duplicateVehicle && isUpdatingToDifferentVehicle) {
            throw new DuplicateVehicleException("Vehicle already exists");
        }

        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setCategory(request.getCategory());
        vehicle.setPrice(request.getPrice());
        vehicle.setQuantity(request.getQuantity());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return mapToResponse(updatedVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new VehicleNotFoundException("Vehicle not found"));

        vehicleRepository.deleteById(vehicle.getId());
    }

    @Override
    @Transactional
    public VehicleResponse purchaseVehicle(Long id, PurchaseRequest request) {

        validatePurchaseQuantity(request.getQuantity());

        Vehicle vehicle = getVehicleById(id);

        validateAvailableStock(vehicle, request.getQuantity());

        vehicle.setQuantity(vehicle.getQuantity() - request.getQuantity());

        return mapToResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional
    public VehicleResponse restockVehicle(Long id, RestockRequest request) {

        validateRestockQuantity(request.getQuantity());

        Vehicle vehicle = getVehicleById(id);

        vehicle.setQuantity(vehicle.getQuantity() + request.getQuantity());

        return mapToResponse(vehicleRepository.save(vehicle));
    }
}
