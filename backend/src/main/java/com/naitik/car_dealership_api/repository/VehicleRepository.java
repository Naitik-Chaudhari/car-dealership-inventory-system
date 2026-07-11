package com.naitik.car_dealership_api.repository;

import com.naitik.car_dealership_api.dto.response.VehicleResponse;
import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    boolean existsByMakeAndModelAndCategory(
            String make,
            String model,
            VehicleCategory category
    );

    List<VehicleResponse> searchVehicles(
            String make,
            String model,
            VehicleCategory category,
            BigDecimal minPrice,
            BigDecimal maxPrice);
}