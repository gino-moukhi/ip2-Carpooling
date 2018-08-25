package be.kdg.ip2.carpooling.domain.route;

import lombok.*;
import org.springframework.data.geo.Point;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteLocation implements Comparable<RouteLocation> {
    private String locationName;
    private Point location;

    @Override
    public boolean equals(Object obj) {
        super.equals(obj);
        int i;
        i = Double.compare(location.getX(), ((RouteLocation) obj).getLocation().getX());
        if (i != 0) return false;
        i = Double.compare(location.getY(), ((RouteLocation) obj).getLocation().getY());
        return i == 0;
    }

    @Override
    public int compareTo(RouteLocation rl) {
        return this.equals(rl) ? 0 : -1;
    }
}
