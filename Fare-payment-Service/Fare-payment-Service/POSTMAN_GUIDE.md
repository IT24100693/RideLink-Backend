# RideLink Fare & Payment Service

This Java 17 / Spring Boot service provides fare estimates, simulated payment recording, and receipt retrieval. It uses a dedicated MongoDB database named `ridelink_fare_payment`; it does not access another microservice's data store and does not connect to a real payment provider.

## Fare rule

The base fare is LKR 100 plus LKR 50 per kilometre. Fare estimates apply a vehicle multiplier: SEDAN 1.0, TUK 0.8, SUV 1.5. The final simulated payment uses the base fare and distance rate, rounded to two decimal places. Route distance is provided by the calling service; no real map service is used.

## Run the service

1. Start MongoDB locally. The default connection is `mongodb://localhost:27017/ridelink_fare_payment`.
2. If needed, set a different connection string in PowerShell: `$env:MONGODB_URI = "mongodb://localhost:27017/ridelink_fare_payment"`.
3. Open PowerShell in this project folder and run:

   ```powershell
   java -version
   mvn spring-boot:run
   ```

   Use Java 17 or newer and have Maven installed. The service listens on port `8084`.
4. Open Swagger UI at `http://localhost:8084/swagger-ui.html` or use the requests below in Postman.

## Postman requests

For JSON requests, choose **Body → raw → JSON**. Set `Content-Type: application/json`.

### 1. Estimate a fare

- Method: `POST`
- URL: `http://localhost:8084/api/v1/fares/estimate`
- Body:

```json
{
  "distanceKm": 8.5,
  "vehicleType": "SEDAN"
}
```

Expected estimate: `LKR 525.00`. Vehicle types are `SEDAN`, `TUK`, or `SUV`.

### 2. Record a simulated payment

- Method: `POST`
- URL: `http://localhost:8084/api/v1/fares/pay`
- Body:

```json
{
  "rideId": "ride-1001",
  "passengerId": "passenger-12",
  "driverId": "driver-07",
  "distanceKm": 8.5,
  "paymentMethod": "CASH"
}
```

Expected response: `200 OK`, with `paymentStatus: PAID`, `finalFare: 525.00`, and a generated payment `id`. Use `CARD` or `WALLET` as other successful simulated methods. Use `INVALID` to demonstrate a simulated failed payment (`paymentStatus: FAILED`). A ride can only have one payment record.

### 3. Retrieve a receipt and payment status

- Method: `GET`
- URL: `http://localhost:8084/api/v1/fares/receipt/ride-1001`

Replace `ride-1001` with the `rideId` you paid for. The response includes receipt number, fare, payment method, and payment status. A ride without a payment returns `404 Not Found`.

### Negative validation examples

- Submit a negative or zero distance to either POST endpoint: expect `400 Bad Request`.
- Estimate with `vehicleType: "VAN"`: expect `400 Bad Request`.
- Submit the same `rideId` for payment twice: expect `409 Conflict`.
- Retrieve a receipt for an unknown ride: expect `404 Not Found`.

OpenAPI JSON is available at `http://localhost:8084/v3/api-docs`.
