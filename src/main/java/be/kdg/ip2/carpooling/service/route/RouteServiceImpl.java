package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.route.*;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.repository.PlaceRepository;
import be.kdg.ip2.carpooling.repository.route.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;
    private PlaceRepository placeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, PlaceRepository placeRepository) {
        this.routeRepository = routeRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public List<Route> findAllRoutes() throws RouteServiceException {
        return routeRepository.findAll();
    }

    @Override
    public List<RouteDto> findAllRoutesAsDto() throws RouteServiceException {
        List<Route> all = routeRepository.findAll();
        List<RouteDto> allDto = new ArrayList<>();

        all.forEach(route -> {
            RouteDto routeDto = new RouteDto(route);
            allDto.add(routeDto);
        });
        log.info("ALL");
        log.info(all.toString());
        log.info("ALL DTO");
        log.info(allDto.toString());
        return allDto;
    }

    @Override
    public Route findRouteById(String id) throws RouteServiceException {
        return routeRepository.findRouteById(id);
    }

    @Override
    public Route addRoute(Route route) throws RouteServiceException {
        return routeRepository.save(route);
    }

    @Override
    public Route addRoute(RouteDto routeDto) throws RouteServiceException {
        log.info(routeDto.toString());
        Route route = new Route(routeDto);
        log.info(route.toString());
        return saveWithCheck(route, false);
    }

    @Override
    public void deleteAll() {
        routeRepository.deleteAll();
    }

    @Override
    public void deleteRouteById(String id) {
        routeRepository.deleteById(id);
    }

    @Override
    public Route saveWithCheck(Route route, boolean useIdOrRouteDefinition) throws RouteServiceException {
        Route foundRoute;
        Route routeToSave;
        log.info("ROUTE TO UPDATE 1: " + route);
        if (useIdOrRouteDefinition) {
            foundRoute = routeRepository.findRouteById(route.getId());
            log.info("ROUTE TO UPDATE 2: " + foundRoute);
            foundRoute = route;
            routeToSave = foundRoute;
            log.info("ROUTE TO UPDATE 3: " + routeToSave);
        } else {
            foundRoute = routeRepository.findRouteByDefinition_OriginAndDefinition_Destination(route.getDefinition().getOrigin(),
                    route.getDefinition().getDestination());
            if (foundRoute != null) {
                route.setId(foundRoute.getId());
            }
            routeToSave = route;
            log.info("THE NEW ROUTE TO SAVE: " + routeToSave);
        }
        return routeRepository.save(routeToSave);
    }

    @Override
    public List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException {
        return routeRepository.findRoutesByVehicleType(type);
    }

    @Override
    public Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName) throws RouteServiceException {
        return routeRepository.findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(startLocationName, finishLocationName);
    }

    @Override
    public List<Route> findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(double originLat, double originLng, double destinationLat, double destinationLng) {
        GeoJsonPoint origin = new GeoJsonPoint(originLat, originLng);
        GeoJsonPoint destination = new GeoJsonPoint(destinationLat, destinationLng);
        return routeRepository.findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(origin, destination);
    }

    @Override
    public List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance) throws RouteServiceException {
        return routeRepository.findRoutesByDefinition_Origin_LocationNear(point, distance);
    }

    @Override
    public Set<Route> findRoutesNearLocations(Point origin, Point destination, Distance distance) {
        List<Place> allPlacesNearOrigin;
        List<Place> allPlacesNearDestination;
        List<Route> allRoutes = routeRepository.findAll();
        Set<Place> places = new TreeSet<>(new PlaceLocationComparator());
        allRoutes.forEach(route -> {
            places.add(new Place(route.getDefinition().getOrigin().getLocationName(),
                    route.getDefinition().getOrigin().getLocation(), SourceType.ORIGIN));
            places.add(new Place(route.getDefinition().getDestination().getLocationName(),
                    route.getDefinition().getDestination().getLocation(), SourceType.DESTINATION));
            route.getDefinition().getWaypoints().forEach(wp ->
                    places.add(new Place(wp.getLocationName(), wp.getLocation(), SourceType.WAYPOINT)));

        });

        places.forEach(place -> {
            if (placeRepository.findPlaceByLocation(place.getLocation()) == null) {
                placeRepository.save(place);
            }
        });
        allPlacesNearOrigin = placeRepository.findPlacesByLocationNear(origin, distance);
        allPlacesNearDestination = placeRepository.findPlacesByLocationNear(destination, distance);
        allPlacesNearOrigin.removeIf(place -> place.getSourceType().equals(SourceType.DESTINATION));
        allPlacesNearDestination.removeIf(place -> place.getSourceType().equals(SourceType.ORIGIN));
        Set<Route> matchingRoutesSet = new TreeSet<>();

        if (!allPlacesNearOrigin.isEmpty() && !allPlacesNearDestination.isEmpty()) {

            allPlacesNearOrigin.forEach(originPlace ->
                    allPlacesNearDestination.forEach(destinationPlace -> {
                        List<Route> existingRoutesFromPlaces = routeRepository.findExistingRoutesFromPlaces(originPlace.getSourceType(),
                                destinationPlace.getSourceType(), originPlace.getLocation(), destinationPlace.getLocation());

                        matchingRoutesSet.addAll(existingRoutesFromPlaces);
                    }));

        }
        log.info(matchingRoutesSet.toString());
        return !matchingRoutesSet.isEmpty() ? matchingRoutesSet : null;
    }
}
