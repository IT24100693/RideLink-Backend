package com.ridelink.farepayment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FareEstimateRequest(
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal distanceKm,
        @NotBlank String vehicleType) { }
