package be.kdg.ip2.carpooling.domain.route;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteLocation {
    private String locationName;
    /*private double lat;
    private double lng;*/
    private GeoJsonPoint location;
}
