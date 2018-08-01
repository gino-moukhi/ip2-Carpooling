package be.kdg.ip2.carpooling.service.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
//import com.mongodb.client.model.geojson.Point;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> findAllUsers() throws RouteServiceException {
        return routeRepository.findAll();
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
        return saveWithCheck(route,false);
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
    public Route findRouteByDefinition_Start_LocationNameAndDefinition_Finish_LocationName(String startLocationName, String finishLocationName) throws RouteServiceException {
        return routeRepository.findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(startLocationName, finishLocationName);
    }

    @Override
    public List<Route> findRoutesByDefinition_Start_LocationNear(Point point, Distance distance) throws RouteServiceException {
        return routeRepository.findRoutesByDefinition_Origin_LocationNear(point, distance);
    }
}
