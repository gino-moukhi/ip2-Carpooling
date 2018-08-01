package be.kdg.ip2.carpooling.repository;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteDefinition;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
//import com.mongodb.client.model.geojson.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String>, QuerydslPredicateExecutor<Route> {
    Route findRouteById(String id);

    List<Route> findRoutesByVehicleType(VehicleType type);

    Route findRouteByDefinition_OriginAndDefinition_Destination(RouteLocation start, RouteLocation finish);

    Route findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(String startLocationName, String finishLocationName);

    List<Route> findRouteByDefinition_Origin_LocationNameAndDefinition_Origin_LocationNear(String locationName, GeoJsonPoint point);

    List<Route> findRoutesByDefinition_Origin_LocationNear(Point point, Distance distance);

}
