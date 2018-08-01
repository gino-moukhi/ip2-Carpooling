package be.kdg.ip2.carpooling.domain.route;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteDefinition {
    private RouteLocation origin;
    private RouteLocation destination;
    private RouteType routeType;
    private List<RouteLocation> waypoints;

    public RouteDefinition(RouteLocation origin, RouteLocation destination, RouteType type) {
        this.origin = origin;
        this.destination = destination;
        this.routeType = type;
        waypoints = new ArrayList<>();
    }
}
