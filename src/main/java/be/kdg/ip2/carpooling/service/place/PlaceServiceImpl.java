package be.kdg.ip2.carpooling.service.place;

import be.kdg.ip2.carpooling.domain.place.Place;
import be.kdg.ip2.carpooling.domain.place.SourceType;
import be.kdg.ip2.carpooling.repository.place.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PlaceServiceImpl implements PlaceService{
    private PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public void insertAll(List<Place> places) {
        placeRepository.saveAll(places);
    }

    @Override
    public void save(Place place) {
        placeRepository.save(place);
    }

    @Override
    public void deleteAll() {
        placeRepository.deleteAll();
    }

    @Override
    public Place findPlaceByLocationAndSourceType(GeoJsonPoint location, SourceType sourceType) {
        return placeRepository.findPlaceByLocationAndSourceType(location, sourceType);
    }

    @Override
    public List<Place> findPlacesByLocationNear(Point point, Distance distance) {
        return placeRepository.findPlacesByLocationNear(point, distance);
    }
}
