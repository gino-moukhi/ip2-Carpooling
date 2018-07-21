package be.kdg.ip2.carpooling.domain.route;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RouteLocation {
    private String locationName;
    private double lat;
    private double lng;
}
