package be.kdg.ip2.carpooling.domain.route;

import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document(collection = "routes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Route implements Comparable<Route> {
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

    }

    @Override
    public int compareTo(Route r) {
        int i;
        if (id == null && r.getId() == null) {
            i = 0;
        } else if (id.isEmpty() && r.getId().isEmpty()){
            i = 0;
        } else {
            i = id.compareTo(r.id);
        }
        if (i != 0) return i;
        i = definition.compareTo(r.getDefinition());
        if (i != 0) return i;
        i = vehicleType.equals(r.getVehicleType()) ? 0 : -1;
        if (i != 0) return i;
        i = departure.compareTo(r.departure);
        if (i != 0) return -1;
        i = Integer.compare(availablePassengers, r.getAvailablePassengers());
        if (i != 0) return i;
        return i;
    }
}
