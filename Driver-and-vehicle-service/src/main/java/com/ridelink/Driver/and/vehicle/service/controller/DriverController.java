package com.ridelink.Driver.and.vehicle.service.controller;

import com.ridelink.Driver.and.vehicle.service.model.AvailabilityStatus;
import com.ridelink.Driver.and.vehicle.service.model.Driver;
import com.ridelink.Driver.and.vehicle.service.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.createDriver(driver);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable String driverId) {

        return driverService.getDriverById(driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public List<Driver> getAvailableDrivers(
            @RequestParam String serviceArea) {

        return driverService.getAvailableDrivers(
                AvailabilityStatus.AVAILABLE,
                serviceArea
        );
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<Driver> updateDriver(
            @PathVariable String driverId,
            @RequestBody Driver driver) {

        if (driverService.getDriverById(driverId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        driver.setDriverId(driverId);

        return ResponseEntity.ok(driverService.updateDriver(driver));
    }

    @PatchMapping("/{driverId}/availability")
    public ResponseEntity<Driver> updateAvailability(
            @PathVariable String driverId,
            @RequestParam AvailabilityStatus status) {

        try {
            return ResponseEntity.ok(
                    driverService.updateAvailability(driverId, status)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{driverId}/location")
    public ResponseEntity<Driver> updateLocation(
            @PathVariable String driverId,
            @RequestParam double latitude,
            @RequestParam double longitude) {

        try {
            return ResponseEntity.ok(
                    driverService.updateLocation(
                            driverId,
                            latitude,
                            longitude
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}