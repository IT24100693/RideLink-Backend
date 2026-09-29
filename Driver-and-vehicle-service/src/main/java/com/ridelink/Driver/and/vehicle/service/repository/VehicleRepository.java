package com.ridelink.Driver.and.vehicle.service.repository;

import com.ridelink.Driver.and.vehicle.service.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    Optional<Vehicle> findByDriverId(String driverId);
}