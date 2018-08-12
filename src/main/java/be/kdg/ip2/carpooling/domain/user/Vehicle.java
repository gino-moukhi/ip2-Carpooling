package be.kdg.ip2.carpooling.domain.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Vehicle {
    private String brand;
    private VehicleType type;
    private double fuelConsumption;
    private int numberOfPassengers;
}
