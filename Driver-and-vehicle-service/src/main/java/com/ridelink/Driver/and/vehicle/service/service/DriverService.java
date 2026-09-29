package com.ridelink.Driver.and.vehicle.service.service;

import com.ridelink.Driver.and.vehicle.service.model.Driver;
import com.ridelink.Driver.and.vehicle.service.model.AvailabilityStatus;
import com.ridelink.Driver.and.vehicle.service.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> getDriverById(String driverId) {
        return driverRepository.findById(driverId);
    }

    public List<Driver> getAvailableDrivers(
            AvailabilityStatus status,
            String serviceArea) {

        return driverRepository
                .findByAvailabilityStatusAndServiceArea(status, serviceArea);
    }

    public Driver updateDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public void deleteDriver(String driverId) {
        driverRepository.deleteById(driverId);
    }

    public Driver updateAvailability(
            String driverId,
            AvailabilityStatus status) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        driver.setAvailabilityStatus(status);

        return driverRepository.save(driver);
    }

    public Driver updateLocation(
            String driverId,
            double latitude,
            double longitude) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        driver.setLatitude(latitude);
        driver.setLongitude(longitude);

        return driverRepository.save(driver);
    }
}