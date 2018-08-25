package be.kdg.ip2.carpooling.domain.search;

import lombok.*;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SearchCriteria {
    private Point origin;
    private Point destination;
    private Distance distance;
    private LocalDateTime minDepartureTime;
    private LocalDateTime maxDepartureTime;
    private SearchCriteriaAcceptanceType gender;
    private SearchCriteriaAcceptanceType smoker;

    public SearchCriteria(Point origin, Point destination, Distance distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        minDepartureTime = LocalDateTime.now();
        maxDepartureTime = LocalDateTime.now().plusSeconds(1);
        gender = SearchCriteriaAcceptanceType.EITHER;
        smoker = SearchCriteriaAcceptanceType.EITHER;
    }
}
