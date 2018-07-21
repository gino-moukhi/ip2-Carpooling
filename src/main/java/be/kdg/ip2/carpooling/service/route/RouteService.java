package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    List<Route> findAllUsers() throws RouteServiceException;

    Route findRouteById(String id) throws RouteServiceException;

    Route addRoute(Route route) throws RouteServiceException;

    void deleteAll();

    void deleteRouteById(String id);

    Route saveWithCheck(Route route, boolean useIdOrRouteDefinition) throws RouteServiceException;

    List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException;

    Route findRouteByDefinition_Start_LocationNameAndDefinition_Finish_LocationName(String startLocationName, String finishLocationName)
            throws RouteServiceException;

}
