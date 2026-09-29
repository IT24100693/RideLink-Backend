package com.ridelink.Driver.and.vehicle.service.controller;

import com.ridelink.Driver.and.vehicle.service.model.Vehicle;
import com.ridelink.Driver.and.vehicle.service.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(
            @PathVariable String vehicleId) {

        return vehicleService.getVehicleById(vehicleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Vehicle> getVehicleByDriverId(
            @PathVariable String driverId) {

        return vehicleService.getVehicleByDriverId(driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String vehicleId,
            @RequestBody Vehicle vehicle) {

        if (vehicleService.getVehicleById(vehicleId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        vehicle.setVehicleId(vehicleId);

        return ResponseEntity.ok(vehicleService.updateVehicle(vehicle));
    }
}