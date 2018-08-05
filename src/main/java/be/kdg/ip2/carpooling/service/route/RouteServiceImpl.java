package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.place.Place;
import be.kdg.ip2.carpooling.domain.place.SourceType;
import be.kdg.ip2.carpooling.domain.route.*;
import be.kdg.ip2.carpooling.domain.search.SearchCriteria;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.repository.place.PlaceRepository;
import be.kdg.ip2.carpooling.repository.route.RouteRepository;
import be.kdg.ip2.carpooling.util.DecimalUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
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
        all.forEach(route -> allDto.add(new RouteDto(route)));
        return allDto;
    }

    @Override
    public RouteDto findRouteById(String id) throws RouteServiceException {
        return new RouteDto(routeRepository.findRouteById(id));
    }

    @Override
    public Route addRoute(Route route) throws RouteServiceException {
        Route rounded = DecimalUtil.roundLocationPoints(route);
        return saveWithCheck(rounded, false);
    }

    @Override
    public Route addRoute(RouteDto routeDto) throws RouteServiceException {
        log.info(routeDto.toString());
        Route route = new Route(routeDto);
        Route rounded = DecimalUtil.roundLocationPoints(route);
        log.info(route.toString());
        return saveWithCheck(rounded, false);
    }

    @Override
    public Route updateRoute(RouteDto routeDto) throws RouteServiceException {
        Route route = new Route(routeDto);
        Route rounded = DecimalUtil.roundLocationPoints(route);
        return saveWithCheck(rounded, true);
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
    public List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException {
        return routeRepository.findRoutesByVehicleType(type);
    }

    @Override
    public Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName) throws RouteServiceException {
        return routeRepository.findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(startLocationName, finishLocationName);
    }

    @Override
    public List<Route> findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(double originLat, double originLng, double destinationLat, double destinationLng) {
        Point origin = new Point(originLat, originLng);
        Point destination = new Point(destinationLat, destinationLng);
        return routeRepository.findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(origin, destination);
    }

    @Override
    public List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance) throws RouteServiceException {
        return routeRepository.findRoutesByDefinition_Origin_LocationNear(point, distance);
    }

    @Override
    public List<RouteDto> findRoutesNearLocationsSimple(SearchCriteria searchCriteria) {
        QRoute qRoute = new QRoute("route");
        BooleanExpression filterByAvailabePassengers = qRoute.availablePassengers.gt(0);
        List<Route> allRoutes = (List<Route>) routeRepository.findAll(filterByAvailabePassengers);
        //List<Route> allRoundedRoutes = new ArrayList<>();
        //allRoutes.forEach(route -> allRoundedRoutes.add(DecimalUtil.roundLocationPoints(route)));
        List<Place> allPlacesNearOrigin;
        List<Place> allPlacesNearDestination;
        Set<Place> places = new TreeSet<>();

        //allRoundedRoutes.forEach(route -> {
        allRoutes.forEach(route -> {
            places.add(new Place(route.getDefinition().getOrigin().getLocationName(),
                    new GeoJsonPoint(route.getDefinition().getOrigin().getLocation()), SourceType.ORIGIN));
            places.add(new Place(route.getDefinition().getDestination().getLocationName(),
                    new GeoJsonPoint(route.getDefinition().getDestination().getLocation()), SourceType.DESTINATION));
            route.getDefinition().getWaypoints().forEach(wp ->
                    places.add(new Place(wp.getLocationName(), new GeoJsonPoint(wp.getLocation()), SourceType.WAYPOINT)));
        });

        places.forEach(place -> {
            if (placeRepository.findPlaceByLocationAndSourceType(place.getLocation(), place.getSourceType()) == null) {
                placeRepository.save(place);
            }
        });
        allPlacesNearOrigin = placeRepository.findPlacesByLocationNear(searchCriteria.getOrigin(), searchCriteria.getDistance());
        allPlacesNearDestination = placeRepository.findPlacesByLocationNear(searchCriteria.getDestination(), searchCriteria.getDistance());
        allPlacesNearOrigin.removeIf(place -> place.getSourceType().equals(SourceType.DESTINATION));
        allPlacesNearDestination.removeIf(place -> place.getSourceType().equals(SourceType.ORIGIN));
        Set<Route> matchingRoutesSet = new TreeSet<>();

        if (!allPlacesNearOrigin.isEmpty() && !allPlacesNearDestination.isEmpty()) {

            allPlacesNearOrigin.forEach(originPlace ->
                    allPlacesNearDestination.forEach(destinationPlace -> {
                        List<Route> existingRoutesFromPlaces = routeRepository.findExistingRoutesFromPlaces(originPlace.getSourceType(),
                                destinationPlace.getSourceType(), new Point(originPlace.getLocation()), new Point(destinationPlace.getLocation()));

                        matchingRoutesSet.addAll(existingRoutesFromPlaces);
                    }));

        }
        log.info(matchingRoutesSet.toString());
        List<RouteDto> routeDtos = new ArrayList<>();
        matchingRoutesSet.forEach(route -> routeDtos.add(new RouteDto(route)));
        return !routeDtos.isEmpty() ? routeDtos : null;
    }

    @Override
    public List<RouteDto> findRoutesNearLocationsAdvanced(SearchCriteria searchCriteria) {
        return null;
    }

    private Route saveWithCheck(Route route, boolean useIdOrRouteDefinition) throws RouteServiceException {
        Route foundRoute;
        Route routeToSave;
        if (useIdOrRouteDefinition) {
            routeToSave = route;
        } else {
            foundRoute = routeRepository.findRouteByDefinition_OriginAndDefinition_Destination(route.getDefinition().getOrigin(),
                    route.getDefinition().getDestination());
            if (foundRoute != null) {
                route.setId(foundRoute.getId());
            }
            routeToSave = route;
        }
        return routeRepository.save(routeToSave);
    }
}
