package com.naitik.car_dealership_api.service.impl;

import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.repository.VehicleRepository;
import com.naitik.car_dealership_api.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse addVehicle(VehicleRequest request) {

        Vehicle vehicle = Vehicle.builder()
                .make(request.getMake())
                .model(request.getModel())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return VehicleResponse.builder()
                .id(savedVehicle.getId())
                .make(savedVehicle.getMake())
                .model(savedVehicle.getModel())
                .category(savedVehicle.getCategory())
                .price(savedVehicle.getPrice())
                .quantity(savedVehicle.getQuantity())
                .build();
    }
}
