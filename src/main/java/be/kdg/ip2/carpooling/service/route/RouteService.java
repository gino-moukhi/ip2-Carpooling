package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
//import com.mongodb.client.model.geojson.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    List<Route> findAllUsers() throws RouteServiceException;

    Route findRouteById(String id) throws RouteServiceException;

    Route addRoute(Route route) throws RouteServiceException;

    Route addRoute(RouteDto routeDto) throws RouteServiceException;

    void deleteAll();

    void deleteRouteById(String id);

    Route saveWithCheck(Route route, boolean useIdOrRouteDefinition) throws RouteServiceException;

    List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException;

    Route findRouteByDefinition_Start_LocationNameAndDefinition_Finish_LocationName(String startLocationName, String finishLocationName)
            throws RouteServiceException;

    List<Route> findRoutesByDefinition_Start_LocationNear(Point point, Distance distance) throws RouteServiceException;
}
