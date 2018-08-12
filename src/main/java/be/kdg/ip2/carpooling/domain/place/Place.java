package be.kdg.ip2.carpooling.domain.place;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "places")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Place implements Comparable<Place> {
    @Id
    private String id;
    private String locationName;
    @GeoSpatialIndexed(name = "location_2dsphere",type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private SourceType sourceType;

    public Place(String locationName, GeoJsonPoint location, SourceType sourceType) {
        this.locationName = locationName;
        this.location = location;
        this.sourceType = sourceType;
    }

    @Override
    public int compareTo(Place p) {
        return this.equals(p) ? 0 : -1;
    }
}
