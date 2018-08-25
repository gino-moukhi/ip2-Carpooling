package be.kdg.ip2.carpooling.domain.route;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "routes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Route implements Comparable<Route> {
    @Id
    private String id;
    private RouteDefinition definition;
    private VehicleType vehicleType;
    private LocalDateTime departure;
    private int availablePassengers;
    private RouteUser owner;
    private List<RouteUser> passengers;
    private List<CommunicationRequest> communicationRequests;

    public Route(RouteDefinition definition, LocalDateTime departure, int availablePassengers, RouteUser owner, List<RouteUser> passengers, List<CommunicationRequest> communicationRequests) {
        this.definition = definition;
        this.vehicleType = owner.getVehicle().getType();
        this.departure = departure;
        this.availablePassengers = availablePassengers;
        this.owner = owner;
        this.passengers = passengers;
        this.communicationRequests = communicationRequests;
    }

    public Route(RouteDefinition definition, LocalDateTime departure, int availablePassengers, RouteUser owner) {
        this.definition = definition;
        this.vehicleType = owner.getVehicle().getType();
        this.departure = departure;
        this.availablePassengers = availablePassengers;
        this.owner = owner;
        this.passengers = new ArrayList<>();
        this.communicationRequests = new ArrayList<>();
    }

    public Route(RouteDto routeDto) {
        this.definition = new RouteDefinition(new RouteLocation(), new RouteLocation(), null, new ArrayList<>());

        this.id = routeDto.getId();
        this.definition.setRouteType(routeDto.getRouteDefinition().getRouteType());
        this.definition.setOrigin(new RouteLocation(routeDto.getRouteDefinition().getOrigin().getName(),
                new Point(routeDto.getRouteDefinition().getOrigin().getLat(),
                        routeDto.getRouteDefinition().getOrigin().getLng())));
        this.definition.setDestination(new RouteLocation(routeDto.getRouteDefinition().getDestination().getName(),
                new Point(routeDto.getRouteDefinition().getDestination().getLat(),
                        routeDto.getRouteDefinition().getDestination().getLng())));

        routeDto.getRouteDefinition().getWaypoints().forEach(wp ->
                this.definition.getWaypoints().add(new RouteLocation(wp.getName(), new Point(wp.getLat(), wp.getLng()))));

        this.vehicleType = routeDto.getVehicleType();
        this.departure = routeDto.getDeparture();
        this.availablePassengers = routeDto.getAvailablePassengers();
        this.owner = new RouteUser(routeDto.getOwner());
        this.passengers = new ArrayList<>();
        routeDto.getPassengers().forEach(userDto -> this.passengers.add(new RouteUser(userDto)));
        this.communicationRequests = new ArrayList<>();
        if (routeDto.getCommunicationRequests() == null) {
            routeDto.setCommunicationRequests(new ArrayList<>());
        } else {
            routeDto.getCommunicationRequests().forEach(requestDto -> this.communicationRequests.add(new CommunicationRequest(requestDto)));
        }
    }

    @Override
    public int compareTo(Route r) {
        return this.equals(r) ? 0 : -1;
    }
}
