package com.ridelink.Driver.and.vehicle.service.service;

import com.ridelink.Driver.and.vehicle.service.model.Vehicle;
import com.ridelink.Driver.and.vehicle.service.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        if (vehicleRepository
                .findByRegistrationNumber(vehicle.getRegistrationNumber())
                .isPresent()) {

            throw new RuntimeException(
                    "Vehicle registration number already exists"
            );
        }

        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> getVehicleById(String vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public Optional<Vehicle> getVehicleByDriverId(String driverId) {
        return vehicleRepository.findByDriverId(driverId);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}