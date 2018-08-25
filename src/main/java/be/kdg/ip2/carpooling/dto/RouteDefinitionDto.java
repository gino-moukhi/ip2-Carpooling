package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.route.RouteType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RouteDefinitionDto {
    private RouteLocationDto origin;
    private RouteLocationDto destination;
    private RouteType routeType;
    private List<RouteLocationDto> waypoints;
}
