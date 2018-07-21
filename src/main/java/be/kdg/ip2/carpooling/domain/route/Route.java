package be.kdg.ip2.carpooling.domain.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Route {
    private RouteDefinition definition;

    private LocalDateTime startTime;
    private int availablePassengers;
}
