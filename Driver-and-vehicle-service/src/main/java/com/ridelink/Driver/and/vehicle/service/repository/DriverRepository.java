package com.ridelink.Driver.and.vehicle.service.repository;

import com.ridelink.Driver.and.vehicle.service.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    List<Driver> findByAvailabilityStatusAndServiceArea(
            com.ridelink.Driver.and.vehicle.service.model.AvailabilityStatus availabilityStatus,
            String serviceArea
    );
}