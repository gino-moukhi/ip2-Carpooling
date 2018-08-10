package be.kdg.ip2.carpooling.domain.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "places")
@Getter
@Setter
@ToString
@NoArgsConstructor
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
        int i;
        if (id == null && p.getId() == null) {
            i = 0;
        } else if (id.isEmpty() && p.getId().isEmpty()) {
            i = 0;
        } else {
            i = id.compareTo(p.id);
        }
        if (i != 0) return i;
        /*i = locationName.compareTo(p.getLocationName());
        if (i != 0) return i;*/
        i = Arrays.equals(location.getCoordinates().toArray(), p.getLocation().getCoordinates().toArray()) ? 0 : -1;
        if (i != 0) return i;
        i = sourceType.compareTo(p.getSourceType());
        if (i != 0) return i;
        return i;
    }
}
