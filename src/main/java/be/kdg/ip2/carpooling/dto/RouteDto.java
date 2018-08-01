package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.user.VehicleType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteDto {
    private RouteDefinitionDto routeDefinition;
    private VehicleType vehicleType;
    private LocalDateTime departure;
    private int availablePassengers;
}
