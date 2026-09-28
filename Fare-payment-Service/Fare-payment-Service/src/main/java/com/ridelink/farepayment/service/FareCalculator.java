package com.ridelink.farepayment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FareCalculator {
    public static final BigDecimal BASE_FARE = new BigDecimal("100.00");
    public static final BigDecimal RATE_PER_KM = new BigDecimal("50.00");

    /** Fare rule: base fare + distance * per-kilometre rate, rounded half-up to 2 decimals. */
    public BigDecimal calculate(BigDecimal distanceKm) {
        return BASE_FARE.add(RATE_PER_KM.multiply(distanceKm)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal estimate(BigDecimal distanceKm, String vehicleType) {
        BigDecimal multiplier = switch (vehicleType.trim().toUpperCase(Locale.ROOT)) {
            case "SEDAN" -> BigDecimal.ONE;
            case "SUV" -> new BigDecimal("1.5");
            case "TUK" -> new BigDecimal("0.8");
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "vehicleType must be SEDAN, TUK, or SUV");
        };
        return calculate(distanceKm).multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }
}
