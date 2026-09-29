package com.ridelink.Driver.and.vehicle.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drivers")
public class Driver {

    @Id
    private String driverId;

    private String accountId;

    private String licenseNumber;

    private AvailabilityStatus availabilityStatus;

    private String serviceArea;

    private double latitude;

    private double longitude;
}