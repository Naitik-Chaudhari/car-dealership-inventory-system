package com.naitik.car_dealership_api.service;

import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    VehicleResponse addVehicle(VehicleRequest request);

    List<VehicleResponse> getAllVehicles();

}