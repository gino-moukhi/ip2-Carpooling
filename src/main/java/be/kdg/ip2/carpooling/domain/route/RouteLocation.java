package be.kdg.ip2.carpooling.domain.route;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Arrays;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteLocation implements Comparable<RouteLocation> {
    private String locationName;
    private GeoJsonPoint location;

    @Override
    public int compareTo(RouteLocation rl) {
        int i;
        /*i = locationName.compareTo(rl.getLocationName());
        if (i != 0) return i;*/
        i = Arrays.equals(location.getCoordinates().toArray(), rl.getLocation().getCoordinates().toArray()) ? 0 : -1;
        return i;
    }
}
