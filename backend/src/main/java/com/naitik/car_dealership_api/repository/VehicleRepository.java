package com.naitik.car_dealership_api.repository;

import com.naitik.car_dealership_api.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByMakeAndModelAndCategory(
            String make,
            String model,
            String category
    );
}