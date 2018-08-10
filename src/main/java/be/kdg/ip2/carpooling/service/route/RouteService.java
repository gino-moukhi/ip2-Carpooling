package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.search.SearchCriteria;
import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.dto.RouteUserDto;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    List<Route> findAllRoutes() throws RouteServiceException;

    List<RouteDto> findAllRoutesAsDto() throws RouteServiceException;

    Route findRouteById(String id) throws RouteServiceException;

    RouteDto findRouteDtoById(String id) throws RouteServiceException;

    Route addRoute(Route route) throws RouteServiceException;

    Route addRoute(RouteDto routeDto) throws RouteServiceException;

    void addCommunicationRequestToRoute(CommunicationRequest request);

    void updateCommunicationRequestOfRoute(CommunicationRequest request);

    //void updateCommunicationRequestStatusOfRoute(String routeId, String communicationRequestId, CommunicationRequestStatus requestStatus);

    void deleteCommunicationRequestOfRoute(String routeId, String communicationRequestId);

    Route updateRoute(RouteDto routeDto) throws RouteServiceException;

    void updateUserOfRoute(RouteUser user) throws RouteServiceException;

    void addPassengerToRoute(String routeId, RouteUserDto routeUserDto) throws RouteServiceException;

    void addPassengerToRoute(String routeId, RouteUser routeUser) throws RouteServiceException;

    void addPassengerToRoute(String routeId, String userId) throws RouteServiceException;

    void deletePassengerFromRoute(String routeId, RouteUserDto routeUserDto);

    void deletePassengerFromRoute(String routeId, RouteUser routeUser);

    void deletePassengerFromRoute(String routeId, String userId);

    void deleteAll();

    void deleteRouteById(String id) throws RouteServiceException;

    List<RouteDto> findRoutesWhereUserIsOwnerOrPassenger(String userId);

    List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException;

    Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName)
            throws RouteServiceException;

    List<Route> findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(double originLat, double originLng, double destinationLat, double destinationLng);

    List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance) throws RouteServiceException;

    List<RouteDto> findRoutesNearLocationsSimple(SearchCriteria searchCriteria);

    List<RouteDto> findRoutesNearLocationsAdvanced(SearchCriteria searchCriteria);
}
