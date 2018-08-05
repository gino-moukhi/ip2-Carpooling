package be.kdg.ip2.carpooling.service.place;

import be.kdg.ip2.carpooling.domain.route.Place;
import be.kdg.ip2.carpooling.repository.place.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
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
    public void deleteAll() {
        placeRepository.deleteAll();
    }

    @Override
    public List<Place> findPlaceByLocationNear(Point point, Distance distance) {
        return placeRepository.findPlacesByLocationNear(point, distance);
    }
}
