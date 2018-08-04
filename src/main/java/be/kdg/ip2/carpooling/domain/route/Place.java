package be.kdg.ip2.carpooling.domain.route;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Place{
    @Id
    private String id;
    private String locationName;
    private GeoJsonPoint location;
    private SourceType sourceType;

    public Place(String locationName, GeoJsonPoint location, SourceType sourceType) {
        this.locationName = locationName;
        this.location = location;
        this.sourceType = sourceType;
    }
}
