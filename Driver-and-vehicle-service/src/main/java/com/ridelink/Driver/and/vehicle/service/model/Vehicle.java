package com.ridelink.Driver.and.vehicle.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String vehicleId;

    private String driverId;

    private VehicleType vehicleType;

    private String make;

    private String model;

    private String registrationNumber;

    private String color;

    private int year;
}