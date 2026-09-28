package com.ridelink.farepayment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ridelink.farepayment.dto.CreatePaymentRequest;
import com.ridelink.farepayment.dto.FareEstimateRequest;
import com.ridelink.farepayment.dto.FareEstimateResponse;
import com.ridelink.farepayment.dto.PaymentReceipt;
import com.ridelink.farepayment.model.Payment;
import com.ridelink.farepayment.service.FareCalculator;
import com.ridelink.farepayment.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fares")
@Tag(name = "Fare and Payment", description = "Fare estimation, simulated payments, and receipt lookup")
public class FareController {
    private final FareCalculator calculator;
    private final PaymentService paymentService;

    public FareController(FareCalculator calculator, PaymentService paymentService) {
        this.calculator = calculator;
        this.paymentService = paymentService;
    }

    @PostMapping("/estimate")
    @Operation(summary = "Estimate a fare", description = "Uses LKR 100 + LKR 50 per km, multiplied by the selected vehicle type.")
    public FareEstimateResponse estimate(@Valid @RequestBody FareEstimateRequest request) {
        return new FareEstimateResponse(request.distanceKm(), FareCalculator.BASE_FARE, FareCalculator.RATE_PER_KM,
                calculator.estimate(request.distanceKm(), request.vehicleType()));
    }

    @PostMapping("/pay")
    @Operation(summary = "Record a simulated payment", description = "Use paymentMethod INVALID to simulate a failed payment.")
    public Payment processPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.create(request);
    }

    @GetMapping("/receipt/{rideId}")
    @Operation(summary = "Get a payment receipt", description = "Returns the payment record and receipt for the specified ride.")
    public PaymentReceipt getReceipt(@PathVariable String rideId) {
        return paymentService.getReceiptByRideId(rideId);
    }
}
