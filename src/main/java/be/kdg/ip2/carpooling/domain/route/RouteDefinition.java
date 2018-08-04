package be.kdg.ip2.carpooling.domain.route;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteDefinition implements Comparable<RouteDefinition> {
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

    @Override
    public int compareTo(RouteDefinition rd) {
        int i;
        i = origin.compareTo(rd.getOrigin());
        if (i != 0) return i;
        i = destination.compareTo(rd.getDestination());
        if (i != 0) return i;
        i = routeType.equals(rd.getRouteType()) ? 0 : -1;
        if (i != 0) return i;

        /*Collections.sort(waypoints);
        Collections.sort(rd.waypoints);*/
        if (waypoints.isEmpty() && rd.getWaypoints().isEmpty()) {
            return 0;
        } else {
            if (waypoints.size() == rd.getWaypoints().size()) {
                for (int j = 0; j < waypoints.size(); j++) {
                    i = waypoints.get(j).compareTo(rd.getWaypoints().get(j));
                    if (i != 0) return i;
                }
            } else return -1;
        }
        return i;
    }
}
