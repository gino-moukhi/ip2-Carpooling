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
import java.util.Set;

@Service
public interface RouteService {
    List<Route> findAllRoutes() throws RouteServiceException;

    List<RouteDto> findAllRoutesAsDto() throws RouteServiceException;

    Route findRouteById(String id) throws RouteServiceException;

    Route addRoute(Route route) throws RouteServiceException;

    Route addRoute(RouteDto routeDto) throws RouteServiceException;

    void deleteAll();

    void deleteRouteById(String id);

    Route saveWithCheck(Route route, boolean useIdOrRouteDefinition) throws RouteServiceException;

    List<Route> findRoutesByVehicleType(VehicleType type) throws RouteServiceException;

    Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName)
            throws RouteServiceException;

    List<Route> findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(double originLat, double originLng, double destinationLat, double destinationLng);

    List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance) throws RouteServiceException;

    Set<Route> findRoutesNearLocations(Point origin, Point destination, Distance distance);
}
