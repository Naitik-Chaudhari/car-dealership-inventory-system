package com.naitik.car_dealership_api.dto.request;

import com.naitik.car_dealership_api.entity.VehicleCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {

    private String make;

    private String model;

    private VehicleCategory category;

    private BigDecimal price;

    private Integer quantity;
}
