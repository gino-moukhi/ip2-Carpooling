package be.kdg.ip2.carpooling.domain.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RouteDefinition {
    private RouteLocation start;
    private RouteLocation finish;
    private RouteType type;
    private List<RouteLocation> waypoints;

    public RouteDefinition(RouteLocation start, RouteLocation finish, RouteType type) {
        this.start = start;
        this.finish = finish;
        this.type = type;
        waypoints = new ArrayList<>();
    }

    public RouteDefinition(RouteLocation start, RouteLocation finish, RouteType type, List<RouteLocation> waypoints) {
        this.start = start;
        this.finish = finish;
        this.type = type;
        this.waypoints = waypoints;
    }
}
