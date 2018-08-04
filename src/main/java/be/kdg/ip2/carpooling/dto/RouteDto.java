package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RouteDto {
    private String id;
    private RouteDefinitionDto routeDefinition;
    private VehicleType vehicleType;
    private LocalDateTime departure;
    private int availablePassengers;

    public RouteDto(RouteDefinitionDto routeDefinition, VehicleType vehicleType, LocalDateTime departure, int availablePassengers) {
        this.routeDefinition = routeDefinition;
        this.vehicleType = vehicleType;
        this.departure = departure;
        this.availablePassengers = availablePassengers;
    }

    public RouteDto(Route route) {
        this.id = route.getId();
        this.routeDefinition = new RouteDefinitionDto(
                new RouteLocationDto(route.getDefinition().getOrigin().getLocationName(),
                        route.getDefinition().getOrigin().getLocation().getX(),
                        route.getDefinition().getOrigin().getLocation().getY()),
                new RouteLocationDto(route.getDefinition().getDestination().getLocationName(),
                        route.getDefinition().getDestination().getLocation().getX(),
                        route.getDefinition().getDestination().getLocation().getY()),
                route.getDefinition().getRouteType(),
                new ArrayList<>());
        route.getDefinition().getWaypoints().forEach(wp -> this.routeDefinition.getWaypoints().add(
                new RouteLocationDto(wp.getLocationName(), wp.getLocation().getX(), wp.getLocation().getY())));
        this.vehicleType = route.getVehicleType();
        this.departure = route.getDeparture();
        this.availablePassengers = route.getAvailablePassengers();
    }
}
