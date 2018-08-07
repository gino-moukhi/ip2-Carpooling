package be.kdg.ip2.carpooling.domain.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vehicle implements Comparable<Vehicle>{
    private String brand;
    private VehicleType type;
    private double fuelConsumption;
    private int numberOfPassengers;

    @Override
    public int compareTo(Vehicle v) {
        int i;
        i = brand.compareTo(v.getBrand());
        if (i != 0) return i;
        i = type.compareTo(v.getType());
        if (i != 0) return i;
        i = Double.compare(fuelConsumption, v.getFuelConsumption());
        if (i != 0) return i;
        i = Integer.compare(numberOfPassengers, v.getNumberOfPassengers());
        if (i != 0) return i;
        return i;
    }
}
