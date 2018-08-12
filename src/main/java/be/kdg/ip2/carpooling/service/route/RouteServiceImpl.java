package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.domain.place.Place;
import be.kdg.ip2.carpooling.domain.place.SourceType;
import be.kdg.ip2.carpooling.domain.route.*;
import be.kdg.ip2.carpooling.domain.search.SearchCriteria;
import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.dto.RouteUserDto;
import be.kdg.ip2.carpooling.repository.route.RouteRepository;
import be.kdg.ip2.carpooling.service.place.PlaceService;
import be.kdg.ip2.carpooling.util.DecimalUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;
    private PlaceService placeService;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, PlaceService placeService) {
        this.routeRepository = routeRepository;
        this.placeService = placeService;
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
    public Route findRouteById(String id) throws RouteServiceException {
        return routeRepository.findRouteById(id);
    }

    @Override
    public RouteDto findRouteDtoById(String id) throws RouteServiceException {
        return new RouteDto(routeRepository.findRouteById(id));
    }

    @Override
    public Route addRoute(Route route) throws RouteServiceException {
        Route rounded = DecimalUtil.roundLocationPoints(route);
        return saveWithCheck(rounded);
    }

    @Override
    public Route addRoute(RouteDto routeDto) throws RouteServiceException {
        log.info(routeDto.toString());
        Route route = new Route(routeDto);
        route = DecimalUtil.roundLocationPoints(route);
        log.info(route.toString());
        return saveWithCheck(route);
    }

    @Override
    public RouteDto addRouteAsDto(RouteDto routeDto) throws RouteServiceException {
        Route route = new Route(routeDto);
        route = DecimalUtil.roundLocationPoints(route);
        return new RouteDto(saveWithCheck(route));
    }

    @Override
    public RouteDto addCommunicationRequestToRoute(CommunicationRequest request) {
        Route routeForRequest = routeRepository.findRouteById(request.getRouteId());
        if (routeForRequest.getCommunicationRequests() == null) {
            routeForRequest.setCommunicationRequests(new ArrayList<>());
        }
        if (!routeForRequest.getCommunicationRequests().contains(request)) {
            routeForRequest.getCommunicationRequests().add(request);
        }
        checkCommunicationRequestStatusAndHandle(routeForRequest, request);
        return new RouteDto(routeForRequest);
    }

    @Override
    public RouteDto addCommunicationRequestToRoute(CommunicationRequestDto requestDto) {
        Route routeForRequest = routeRepository.findRouteById(requestDto.getRouteId());
        CommunicationRequest request = new CommunicationRequest(requestDto);
        if (routeForRequest.getCommunicationRequests() == null) {
            routeForRequest.setCommunicationRequests(new ArrayList<>());
        }
        if (!routeForRequest.getCommunicationRequests().contains(request)) {
            routeForRequest.getCommunicationRequests().add(request);
        }
        checkCommunicationRequestStatusAndHandle(routeForRequest, request);
        return new RouteDto(routeForRequest);
    }

    @Override
    public RouteDto updateCommunicationRequestOfRoute(CommunicationRequest communicationRequest) {
        return updateCommunicationRequest(communicationRequest);
    }

    @Override
    public RouteDto updateCommunicationRequestOfRoute(CommunicationRequestDto requestDto) {
        CommunicationRequest communicationRequest = new CommunicationRequest(requestDto);
        return updateCommunicationRequest(communicationRequest);
    }

    /*@Override
    public void updateCommunicationRequestStatusOfRoute(String routeId, String communicationRequestId, CommunicationRequestStatus requestStatus) {
        Route routeToUpdate = routeRepository.findRouteById(routeId);
        routeToUpdate.getCommunicationRequests().forEach(request -> {
            if (request.getId().equals(communicationRequestId)) {
                request.setRequestStatus(requestStatus);
                checkCommunicationRequestStatusAndHandle(routeToUpdate, request.getUserId(), request.getRequestStatus());
            }
        });
    }*/

    @Override
    public void deleteCommunicationRequestOfRoute(String routeId, String communicationRequestId) {
        Route routeToDelete = routeRepository.findRouteById(routeId);
        routeToDelete.getCommunicationRequests().forEach(request -> {
            if (request.getId().equals(communicationRequestId)) {
                deletePassengersFromRouteAndUpdateAvailablePassengers(routeToDelete, request.getUser().getId());
                deleteWaypointsFromRoute(routeToDelete, request.getId(), request.getOrigin(), request.getDestination());
            }
        });
        routeToDelete.getCommunicationRequests().removeIf(request -> request.getId().equals(communicationRequestId));
        routeRepository.save(routeToDelete);
    }

    @Override
    public Route updateRoute(RouteDto routeDto) throws RouteServiceException {
        Route route = new Route(routeDto);
        Route rounded = DecimalUtil.roundLocationPoints(route);
        return routeRepository.save(rounded);
    }

    @Override
    public RouteDto updateRouteAsDto(RouteDto routeDto) throws RouteServiceException {
        Route route = new Route(routeDto);
        Route rounded = DecimalUtil.roundLocationPoints(route);
        return new RouteDto(routeRepository.save(rounded));
    }

    @Override
    public void updateUsersOfRoute(RouteUser user) throws RouteServiceException {
        Set<Route> uniqueRoutes = new TreeSet<>();
        List<Route> foundRoutes = routeRepository.findRoutesWhereUserIsOwnerOrPassenger(user.getId());
        List<Route> foundRoutes2 = routeRepository.findRoutesByCommunicationRequestUser(user.getId());
        uniqueRoutes.addAll(foundRoutes);
        uniqueRoutes.addAll(foundRoutes2);
        uniqueRoutes.forEach(route -> {
            boolean isOwner = false;
            if (route.getOwner().getId().equals(user.getId())) {
                route.setOwner(user);
                isOwner = true;
            }
            if (isOwner && !route.getVehicleType().equals(user.getVehicle().getType())) {
                route.setVehicleType(user.getVehicle().getType());
            }
            if (isOwner && route.getAvailablePassengers() != user.getVehicle().getNumberOfPassengers() - route.getPassengers().size()) {
                route.setAvailablePassengers(user.getVehicle().getNumberOfPassengers() - route.getPassengers().size());
            }
            route.getPassengers().forEach(passenger -> {
                if (passenger.getId().equals(user.getId())) {
                    route.getPassengers().set(route.getPassengers().indexOf(passenger), user);
                }
            });
            route.getCommunicationRequests().forEach(request -> {
                if (request.getUser().getId().equals(user.getId())) {
                    request.setUser(user);
                }
            });
        });
        routeRepository.saveAll(uniqueRoutes);
    }

    @Override
    public void addPassengerToRoute(String routeId, RouteUserDto routeUserDto) throws RouteServiceException {
        addPassengersToRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), new RouteUser(routeUserDto));
    }

    @Override
    public void addPassengerToRoute(String routeId, RouteUser routeUser) throws RouteServiceException {
        addPassengersToRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), routeUser);
    }

    @Override
    public void addPassengerToRoute(String routeId, String userId) throws RouteServiceException {
        //addPassengersToRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), userId);
    }

    @Override
    public void deletePassengerFromRoute(String routeId, RouteUserDto routeUserDto) {
        deletePassengersFromRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), routeUserDto.getId());
    }

    @Override
    public void deletePassengerFromRoute(String routeId, RouteUser routeUser) {
        deletePassengersFromRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), routeUser.getId());
    }

    @Override
    public void deletePassengerFromRoute(String routeId, String userId) {
        deletePassengersFromRouteAndUpdateAvailablePassengers(routeRepository.findRouteById(routeId), userId);
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
    public List<RouteDto> findRoutesWhereUserIsOwnerOrPassenger(String userId) {
        List<Route> all = routeRepository.findRoutesWhereUserIsOwnerOrPassenger(userId);
        List<RouteDto> allDto = new ArrayList<>();
        all.forEach(route -> allDto.add(new RouteDto(route)));
        return allDto;
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
        return findRoutesFromCriteria(searchCriteria, true);
    }

    @Override
    public List<RouteDto> findRoutesNearLocationsAdvanced(SearchCriteria searchCriteria) {
        return findRoutesFromCriteria(searchCriteria, false);
    }

    private List<Route> applyBasicQueryFilters(/*boolean passengerFilter,*/ boolean departureFilter, boolean genderFilter, boolean smokerFilter, SearchCriteria searchCriteria) {
        QRoute qRoute = new QRoute("route");
        BooleanExpression filterByAvailablePassengers = null;
        BooleanExpression filterByDepartureTime = null;
        BooleanExpression filterByGender = null;
        BooleanExpression filterBySmoker = null;

        filterByAvailablePassengers = qRoute.availablePassengers.gt(0);
        /*if (passengerFilter) {
            filterByAvailablePassengers = qRoute.availablePassengers.gt(0);
        }*/
        if (departureFilter) {
            filterByDepartureTime = qRoute.departure.between(searchCriteria.getMinDepartureTime().plusHours(2),
                    searchCriteria.getMaxDepartureTime().plusHours(2));
        }
        if (genderFilter) {
            switch (searchCriteria.getGender()) {
                case OPTION1:
                    filterByGender = qRoute.owner.gender.eq(Gender.MALE);
                    break;
                case OPTION2:
                    filterByGender = qRoute.owner.gender.eq(Gender.FEMALE);
                    break;
                case EITHER:
                    filterByGender = qRoute.owner.gender.eq(Gender.MALE).or(qRoute.owner.gender.eq(Gender.FEMALE));
                    break;
            }
        }
        if (smokerFilter) {
            switch (searchCriteria.getSmoker()) {
                case OPTION1:
                    filterBySmoker = qRoute.owner.smoker.eq(true);
                    break;
                case OPTION2:
                    filterBySmoker = qRoute.owner.smoker.eq(false);
                    break;
                case EITHER:
                    filterBySmoker = qRoute.owner.smoker.eq(true).or(qRoute.owner.smoker.eq(false));
                    break;
            }
        }
        return (List<Route>) routeRepository.findAll(filterByAvailablePassengers.and(filterBySmoker).and(filterByGender).and(filterByDepartureTime));
    }

    private void preparePlaceRepoForGeoSpatialQuery(List<Route> allRoutes) {
        Set<Place> places = new TreeSet<>();

        allRoutes.forEach(route -> {
            places.add(new Place(route.getDefinition().getOrigin().getLocationName(),
                    new GeoJsonPoint(route.getDefinition().getOrigin().getLocation()), SourceType.ORIGIN));
            places.add(new Place(route.getDefinition().getDestination().getLocationName(),
                    new GeoJsonPoint(route.getDefinition().getDestination().getLocation()), SourceType.DESTINATION));
            route.getDefinition().getWaypoints().forEach(wp ->
                    places.add(new Place(wp.getLocationName(), new GeoJsonPoint(wp.getLocation()), SourceType.WAYPOINT)));
        });

        places.forEach(place -> {
            if (placeService.findPlaceByLocationAndSourceType(place.getLocation(), place.getSourceType()) == null) {
                placeService.save(place);
            }
        });
    }

    private Set<Route> executeGeoSpatialQueryAndFindMatchingRoutes(List<Route> filteredRoutes, SearchCriteria searchCriteria) {
        List<Place> allPlacesNearOrigin = placeService.findPlacesByLocationNear(searchCriteria.getOrigin(), searchCriteria.getDistance());
        List<Place> allPlacesNearDestination = placeService.findPlacesByLocationNear(searchCriteria.getDestination(), searchCriteria.getDistance());
        allPlacesNearOrigin.removeIf(place -> place.getSourceType().equals(SourceType.DESTINATION));
        allPlacesNearDestination.removeIf(place -> place.getSourceType().equals(SourceType.ORIGIN));
        Set<Route> matchingRoutesSet = new TreeSet<>();

        if (!allPlacesNearOrigin.isEmpty() && !allPlacesNearDestination.isEmpty()) {
            allPlacesNearOrigin.forEach(originPlace ->
                    allPlacesNearDestination.forEach(destinationPlace -> {
                        /*List<Route> existingRoutesFromPlaces = routeRepository.findExistingRoutesFromPlaces(originPlace.getSourceType(),
                                destinationPlace.getSourceType(), new RouteLocation(originPlace.getLocationName(), new Point(originPlace.getLocation())),
                                new RouteLocation(destinationPlace.getLocationName(), new Point(destinationPlace.getLocation())));
                        matchingRoutesSet.addAll();*/

                        filteredRoutes.forEach(route -> {
                            RouteLocation originRouteLocation = new RouteLocation(originPlace.getLocationName(), new Point(originPlace.getLocation()));
                            RouteLocation destinationRouteLocation = new RouteLocation(destinationPlace.getLocationName(), new Point(destinationPlace.getLocation()));
                            if ((route.getDefinition().getOrigin().equals(originRouteLocation) ||
                                    route.getDefinition().getWaypoints().contains(originRouteLocation)) &&
                                    (route.getDefinition().getDestination().equals(destinationRouteLocation) ||
                                            route.getDefinition().getWaypoints().contains(destinationRouteLocation))) {
                                matchingRoutesSet.add(route);
                            }
                        });
                    }));
        }
        log.info(matchingRoutesSet.toString());
        return matchingRoutesSet;
    }

    private List<RouteDto> findRoutesFromCriteria(SearchCriteria searchCriteria, boolean simpleOrAdvanced) {
        List<Route> allFilteredRoutes;
        if (simpleOrAdvanced) {
            allFilteredRoutes = applyBasicQueryFilters(false, false, false, searchCriteria);
        } else {
            allFilteredRoutes = applyBasicQueryFilters(true, true, true, searchCriteria);
        }
        preparePlaceRepoForGeoSpatialQuery(allFilteredRoutes);
        Set<Route> matchingRoutesSet = executeGeoSpatialQueryAndFindMatchingRoutes(allFilteredRoutes, searchCriteria);
        List<RouteDto> routeDtos = new ArrayList<>();
        matchingRoutesSet.forEach(route -> routeDtos.add(new RouteDto(route)));
        return routeDtos;
    }

    private RouteDto updateCommunicationRequest(CommunicationRequest communicationRequest) {
        Route routeToUpdate = routeRepository.findRouteById(communicationRequest.getRouteId());
        routeToUpdate.getCommunicationRequests().forEach(request -> {
            if (request.getId().equals(communicationRequest.getId())) {
                routeToUpdate.getCommunicationRequests().set(routeToUpdate.getCommunicationRequests().indexOf(request),communicationRequest);
                log.info("UPDATED: communication request " + request.getId() + " in route " + routeToUpdate.getId());
                checkCommunicationRequestStatusAndHandle(routeToUpdate, communicationRequest);
            }
        });
        return new RouteDto(routeToUpdate);
    }

    private void checkCommunicationRequestStatusAndHandle(Route route, CommunicationRequest request) {
        try {
            switch (request.getRequestStatus()) {
                case IN_PROGRESS:
                    break;
                case ACCEPTED:
                    addPassengersToRouteAndUpdateAvailablePassengers(route, request.getUser());
                    addWaypointsToRoute(route, request.getOrigin(), request.getDestination());
                    break;
                case DECLINED:
                    deletePassengersFromRouteAndUpdateAvailablePassengers(route, request.getUser().getId());
                    deleteWaypointsFromRoute(route, request.getId(), request.getOrigin(), request.getDestination());
                    break;
            }
            routeRepository.save(route);
        } catch (RouteServiceException e) {
            log.error("Something went wrong with the adding or removing from passengers to the route", e);
        }
    }

    private void addPassengersToRouteAndUpdateAvailablePassengers(Route route, RouteUser user) throws RouteServiceException {
        if (route.getAvailablePassengers() > 0 && !route.getPassengers().contains(user)) {
            route.getPassengers().add(user);
            route.setAvailablePassengers(route.getAvailablePassengers() - 1);
        }
    }

    private void deletePassengersFromRouteAndUpdateAvailablePassengers(Route route, String userId) {
        int passengersBeforeDeleteCall = route.getPassengers().size();
        route.getPassengers().removeIf(routeUser -> routeUser.getId().equals(userId));
        if (route.getPassengers().size() != passengersBeforeDeleteCall) {
            route.setAvailablePassengers(route.getAvailablePassengers() + 1);
        }
    }

    private void addWaypointsToRoute(Route route, RouteLocation wp1, RouteLocation wp2) {
        waypointCheckerAndHandler(route, wp1, null, CommunicationRequestStatus.ACCEPTED);
        waypointCheckerAndHandler(route, wp2, null, CommunicationRequestStatus.ACCEPTED);
    }

    private void deleteWaypointsFromRoute(Route route, String requestId, RouteLocation wp1, RouteLocation wp2) {
        waypointCheckerAndHandler(route, wp1, requestId, CommunicationRequestStatus.DECLINED);
        waypointCheckerAndHandler(route, wp2, requestId, CommunicationRequestStatus.DECLINED);
    }

    private void waypointCheckerAndHandler(Route route, RouteLocation wp, @Nullable String requestId, CommunicationRequestStatus status) {
        if (status.equals(CommunicationRequestStatus.ACCEPTED)) {
            if (!route.getDefinition().getOrigin().equals(wp) && !route.getDefinition().getDestination().equals(wp)) {
                if (!route.getDefinition().getWaypoints().contains(wp)) {
                    route.getDefinition().getWaypoints().add(wp);
                }
            }
        } else if (status.equals(CommunicationRequestStatus.DECLINED)) {
            Set<RouteLocation> otherUsedWaypoints = new TreeSet<>();
            route.getCommunicationRequests().forEach(request -> {
                if (!request.getId().equals(requestId) && request.getRequestStatus().equals(CommunicationRequestStatus.ACCEPTED)) {
                    otherUsedWaypoints.add(request.getOrigin());
                    otherUsedWaypoints.add(request.getDestination());
                }
            });
            if (!otherUsedWaypoints.contains(wp)) {
                route.getDefinition().getWaypoints().removeIf(waypoint -> waypoint.equals(wp));
            }
        }
    }

    private Route saveWithCheck(Route route) {
        Route foundRoute = routeRepository.findRouteByDefinition_OriginAndDefinition_Destination(route.getDefinition().getOrigin(),
                route.getDefinition().getDestination());
        if (foundRoute != null) {
            route.setId(foundRoute.getId());
        }
        return routeRepository.save(route);
    }
}
