package be.kdg.ip2.carpooling.repository.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.place.SourceType;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRouteRepository {
    List<Route> findExistingRoutesFromPlaces(SourceType sourceType1, SourceType sourceType2, RouteLocation origin, RouteLocation destination);

    List<Route> findRoutesWhereUserIsOwnerOrPassenger(String userId);

    List<Route> findRoutesByCommunicationRequestUser(String userId);
}
