package be.kdg.ip2.carpooling.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Vehicle {
    private String brand;
    private VehicleType type;
    private double fuelConsumption;
    private int numberOfPassengers;

    public Vehicle(String brand, VehicleType type, double fuelConsumption, int numberOfPassengers) {
        this.brand = brand;
        this.type = type;
        this.fuelConsumption = fuelConsumption;
        this.numberOfPassengers = numberOfPassengers;
    }
}
