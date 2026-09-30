package com.ridelink.farepayment.service;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ridelink.farepayment.dto.CreatePaymentRequest;
import com.ridelink.farepayment.dto.PaymentReceipt;
import com.ridelink.farepayment.model.Payment;
import com.ridelink.farepayment.model.Payment.PaymentStatus;
import com.ridelink.farepayment.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final FareCalculator fareCalculator;

    public PaymentService(PaymentRepository repository, FareCalculator fareCalculator) {
        this.repository = repository;
        this.fareCalculator = fareCalculator;
    }

    public Payment create(CreatePaymentRequest request) {
        if (repository.findByRideId(request.rideId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A payment already exists for this ride");
        }
        Payment payment = new Payment();
        payment.setRideId(request.rideId());
        payment.setPassengerId(request.passengerId());
        payment.setDriverId(request.driverId());
        payment.setDistanceKm(request.distanceKm());
        payment.setEstimatedFare(fareCalculator.calculate(request.distanceKm()));
        payment.setFinalFare(fareCalculator.calculate(request.distanceKm()));
        payment.setPaymentMethod(request.paymentMethod().trim().toUpperCase(java.util.Locale.ROOT));
        payment.setPaymentStatus("INVALID".equalsIgnoreCase(request.paymentMethod())
                ? PaymentStatus.FAILED : PaymentStatus.PAID);
        payment.setCreatedAt(Instant.now());
        return repository.save(payment);
    }

    public Payment findByRideId(String rideId) {
        return repository.findByRideId(rideId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for ride"));
    }

    public PaymentReceipt getReceiptByRideId(String rideId) {
        Payment payment = findByRideId(rideId);
        return new PaymentReceipt(
                "RCP-" + payment.getId(), payment.getId(), payment.getRideId(), payment.getPassengerId(),
                payment.getDriverId(), payment.getDistanceKm(), "LKR", payment.getEstimatedFare(), payment.getFinalFare(),
                payment.getPaymentMethod(), payment.getPaymentStatus(), payment.getCreatedAt());
    }

}
