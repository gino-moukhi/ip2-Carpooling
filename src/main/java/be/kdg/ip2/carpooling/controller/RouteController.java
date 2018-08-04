package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.service.route.PlaceService;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
//import com.mongodb.client.model.geojson.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/routes")
@Slf4j
public class RouteController {
    private RouteService routeService;
    private PlaceService placeService;

    @Autowired
    public RouteController(RouteService routeService, PlaceService placeService) {
        this.routeService = routeService;
        this.placeService = placeService;
    }

    @GetMapping("/all")
    public List<RouteDto> getAll() throws RouteServiceException {
        return routeService.findAllRoutesAsDto();
    }

    @GetMapping("/{id}")
    public Route getRoute(@PathVariable("id") String id) throws RouteServiceException {
        return routeService.findRouteById(id);
    }

    @PostMapping
    public Route insert(@RequestBody RouteDto dto) throws RouteServiceException {
        return routeService.addRoute(dto);
    }

    @PutMapping
    public Route update(@RequestBody Route route) throws RouteServiceException {
        return routeService.saveWithCheck(route, true);
    }

    @DeleteMapping
    public void deleteAll() {
        routeService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteAll(@PathVariable("id") String id) {
        routeService.deleteRouteById(id);
    }


    @GetMapping("/location/{lat}/{long}/{distance}")
    public List<Route> getRoutesNearLocation(@PathVariable("lat") double lat,
                                             @PathVariable("long") double lng,
                                             @PathVariable("distance") double distance) throws RouteServiceException {
        return routeService.findRoutesByDefinition_Origin_LocationNear(new Point(lat, lng), new Distance(distance, Metrics.KILOMETERS));
    }

    @GetMapping("/location/{lat}/{long}/{lat1}/{long1}/{distance}")
    public Set<Route> getRoutesNearLocations(@PathVariable("lat") double lat,
                                              @PathVariable("long") double lng,
                                              @PathVariable("lat1") double lat1,
                                              @PathVariable("long1") double lng1,
                                              @PathVariable("distance") double d) throws RouteServiceException {
        //return routeService.findRoutesByDefinition_Origin_LocationNear(new Point(lat, lng), new Distance(distance, Metrics.KILOMETERS));
        //return routeService.findRoutesByDefinition_Origin_LocationNearAndDefinition_Destination_LocationNear(new Point(lat, lng), new Point(lat1, lng1), new Distance(distance, Metrics.KILOMETERS));
        //List<Route> foundRoutes = routeService.findRoutesByDefinition_Origin_LocationAndDefinition_Destination_Location(lat, lng, lat1, lng1);
        Point origin = new Point(lat, lng);
        Point destination = new Point(lat1, lng1);
        Distance distance = new Distance(d, Metrics.KILOMETERS);
        return routeService.findRoutesNearLocations(origin, destination, distance);
    }
}
