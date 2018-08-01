package be.kdg.ip2.carpooling.domain.route;

import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public Route(RouteDto routeDto) {
        //this.definition = routeDto.getRouteDefinition();
        this.definition = new RouteDefinition(new RouteLocation(), new RouteLocation(), routeDto.getRouteDefinition().getRouteType(), new ArrayList<>());

        this.definition.setRouteType(routeDto.getRouteDefinition().getRouteType());
        this.definition.setOrigin(new RouteLocation(routeDto.getRouteDefinition().getOrigin().getName(),
                new GeoJsonPoint(routeDto.getRouteDefinition().getOrigin().getLat(),
                        routeDto.getRouteDefinition().getOrigin().getLng())));
        this.definition.setDestination(new RouteLocation(routeDto.getRouteDefinition().getDestination().getName(),
                new GeoJsonPoint(routeDto.getRouteDefinition().getDestination().getLat(),
                        routeDto.getRouteDefinition().getDestination().getLng())));

        routeDto.getRouteDefinition().getWaypoints().forEach(wp -> {
            this.definition.getWaypoints().add(new RouteLocation(wp.getName(), new GeoJsonPoint(wp.getLat(), wp.getLng())));
        });

        this.vehicleType = routeDto.getVehicleType();
        this.departure = routeDto.getDeparture();
        this.availablePassengers = routeDto.getAvailablePassengers();

    }
}
