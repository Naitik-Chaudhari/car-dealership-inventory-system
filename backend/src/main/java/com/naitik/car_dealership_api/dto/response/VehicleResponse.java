package com.naitik.car_dealership_api.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

    private Long id;

    private String make;

    private String model;

    private String category;

    private BigDecimal price;

    private Integer quantity;
}
