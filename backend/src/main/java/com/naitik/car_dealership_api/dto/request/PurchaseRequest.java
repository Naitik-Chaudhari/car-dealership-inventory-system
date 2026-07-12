package com.naitik.car_dealership_api.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
