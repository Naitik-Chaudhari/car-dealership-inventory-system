package com.naitik.car_dealership_api.controller;

import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import com.naitik.car_dealership_api.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse addVehicle(@Valid @RequestBody VehicleRequest request) {
        return vehicleService.addVehicle(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/search")
    public List<VehicleResponse> searchVehicles(

            @RequestParam(required = false) String make,

            @RequestParam(required = false) String model,

            @RequestParam(required = false)
            VehicleCategory category,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice) {

        return vehicleService.searchVehicles(
                make,
                model,
                category,
                minPrice,
                maxPrice);
    }
}