package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.route.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {
    List<Place> findAllPlaces();

    void insertAll(List<Place> places);

    void deleteAll();

    List<Place> findPlaceByLocationNear(Point point, Distance distance);
}
