package com.ridelink.farepayment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreatePaymentRequest(
        @NotBlank String rideId,
        @NotBlank String passengerId,
        @NotBlank String driverId,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal distanceKm,
        @NotBlank @Pattern(regexp = "(?i)CASH|CARD|WALLET|INVALID") String paymentMethod) { }
