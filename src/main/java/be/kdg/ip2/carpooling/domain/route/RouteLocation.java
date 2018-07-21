package be.kdg.ip2.carpooling.domain.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RouteLocation {
    private String locationName;
    private double lat;
    private double lng;

    public RouteLocation(String locationName, double lat, double lng) {
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
    }
}
