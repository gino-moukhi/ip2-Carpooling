package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RouteDto {
    private String id;
    private RouteDefinitionDto routeDefinition;
    private VehicleType vehicleType;
    private LocalDateTime departure;
    private int availablePassengers;
    private RouteUserDto owner;
    private List<RouteUserDto> passengers;
    private List<CommunicationRequestDto> communicationRequests;

    public RouteDto(RouteDefinitionDto routeDefinition, LocalDateTime departure, int availablePassengers, RouteUserDto owner, List<RouteUserDto> passengers, List<CommunicationRequestDto> communicationRequests) {
        this.routeDefinition = routeDefinition;
        this.vehicleType = owner.getVehicle().getType();
        this.departure = departure;
        this.availablePassengers = availablePassengers;
        this.owner = owner;
        this.passengers = passengers;
        this.communicationRequests = communicationRequests;
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
        this.owner = new RouteUserDto(route.getOwner());
        this.passengers = new ArrayList<>();
        route.getPassengers().forEach(user -> this.passengers.add(new RouteUserDto(user)));
        this.communicationRequests = new ArrayList<>();
        if (route.getCommunicationRequests() == null) {
            route.setCommunicationRequests(new ArrayList<>());
        } else {
            route.getCommunicationRequests().forEach( request -> this.communicationRequests.add(new CommunicationRequestDto(request)));
        }
    }
}
