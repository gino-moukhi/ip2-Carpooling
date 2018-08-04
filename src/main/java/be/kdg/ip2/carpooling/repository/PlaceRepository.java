package be.kdg.ip2.carpooling.repository;

import be.kdg.ip2.carpooling.domain.route.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
    List<Place> findPlaceById(String id);

    Place findPlaceByLocation(GeoJsonPoint location);

    List<Place> findPlacesByLocationNear(Point point, Distance distance);
}
