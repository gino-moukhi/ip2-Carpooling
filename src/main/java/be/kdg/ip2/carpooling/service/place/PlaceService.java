package be.kdg.ip2.carpooling.service.place;

import be.kdg.ip2.carpooling.domain.place.Place;
import be.kdg.ip2.carpooling.domain.place.SourceType;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {
    List<Place> findAllPlaces();

    void insertAll(List<Place> places);

    void save(Place place);

    void deleteAll();

    Place findPlaceByLocationAndSourceType(GeoJsonPoint location, SourceType sourceType);

    List<Place> findPlacesByLocationNear(Point point, Distance distance);
}
