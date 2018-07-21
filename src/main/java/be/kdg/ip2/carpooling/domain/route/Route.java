package be.kdg.ip2.carpooling.domain.route;

import be.kdg.ip2.carpooling.domain.user.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "routes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Route {
    @Id
    private String id;
    private RouteDefinition definition;
    private VehicleType vehicleType;
    private LocalDateTime departure;
    private int availablePassengers;

    public Route(RouteDefinition definition, VehicleType vehicleType, LocalDateTime departure, int availablePassengers) {
        this.definition = definition;
        this.vehicleType = vehicleType;
        this.departure = departure;
        this.availablePassengers = availablePassengers;
    }
}
