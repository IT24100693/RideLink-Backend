package com.ridelink.farepayment.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.ridelink.farepayment.model.Payment.PaymentStatus;

public record PaymentReceipt(
        String receiptNumber,
        String paymentId,
        String rideId,
        String passengerId,
        String driverId,
        BigDecimal distanceKm,
        String currency,
        BigDecimal estimatedFare,
        BigDecimal finalFare,
        String paymentMethod,
        PaymentStatus paymentStatus,
        Instant issuedAt) { }
