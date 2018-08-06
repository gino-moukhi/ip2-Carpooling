package be.kdg.ip2.carpooling.domain.route;

import lombok.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Arrays;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteLocation implements Comparable<RouteLocation> {
    private String locationName;
    private Point location;

    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        int i = this.compareTo((RouteLocation) obj);
        equals = i == 0;
        return equals;
    }

    @Override
    public int compareTo(RouteLocation rl) {
        int i;
        /*i = locationName.compareTo(rl.getLocationName());
        if (i != 0) return i;*/
        i = Double.compare(location.getX(), rl.getLocation().getX());
        if (i != 0) return i;
        i = Double.compare(location.getY(), rl.getLocation().getY());
        if (i != 0) return i;
        return i;
    }
}
