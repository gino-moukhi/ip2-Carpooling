package be.kdg.ip2.carpooling.repository.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String>, QuerydslPredicateExecutor<Route>, CustomRouteRepository {
    Route findRouteById(String id);

    List<Route> findRoutesByVehicleType(VehicleType type);

    Route findRouteByDefinition_OriginAndDefinition_Destination(RouteLocation origin, RouteLocation destination);

    Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName);

    List<Route> findRouteByDefinition_Origin_LocationNameAndDefinition_Origin_LocationNear(String locationName, Point point);

    List<Route> findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(Point origin, Point destination);

    List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance);

    List<Route> findRoutesByDefinition_Destination_LocationNear(Point point, Distance distance);
}
