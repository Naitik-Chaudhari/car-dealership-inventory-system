package com.naitik.car_dealership_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naitik.car_dealership_api.dto.request.VehicleRequest;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import com.naitik.car_dealership_api.service.CustomUserDetailsService;
import com.naitik.car_dealership_api.service.JwtService;
import com.naitik.car_dealership_api.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VehicleService vehicleService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldReturnBadRequestWhenMakeIsBlank() throws Exception {

        VehicleRequest request = VehicleRequest.builder()
                .make("")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenModelIsBlank() throws Exception {

        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCategoryIsNull() throws Exception {

        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("Fortuner")
                .category(null)
                .price(BigDecimal.valueOf(4500000))
                .quantity(5)
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {

        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(-100))
                .quantity(5)
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenQuantityIsNegative() throws Exception {

        VehicleRequest request = VehicleRequest.builder()
                .make("Toyota")
                .model("Fortuner")
                .category(VehicleCategory.SUV)
                .price(BigDecimal.valueOf(4500000))
                .quantity(-1)
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
