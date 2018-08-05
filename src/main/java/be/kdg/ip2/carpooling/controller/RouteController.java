package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.search.SearchCriteria;
import be.kdg.ip2.carpooling.domain.search.SearchCriteriaAcceptanceType;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/routes")
@Slf4j
public class RouteController {
    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<RouteDto> getAll() throws RouteServiceException {
        return routeService.findAllRoutesAsDto();
    }

    @GetMapping("/{id}")
    public RouteDto getRoute(@PathVariable("id") String id) throws RouteServiceException {
        return routeService.findRouteById(id);
    }

    @PostMapping
    public Route insert(@RequestBody RouteDto dto) throws RouteServiceException {
        return routeService.addRoute(dto);
    }

    @PutMapping
    public Route update(@RequestBody RouteDto route) throws RouteServiceException {
        return routeService.updateRoute(route);
    }

    @DeleteMapping
    public void deleteAll() {
        routeService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteAll(@PathVariable("id") String id) {
        routeService.deleteRouteById(id);
    }


    /*@GetMapping("/location/{lat}/{long}/{distance}")
    public List<Route> getRoutesNearLocation(@PathVariable("lat") double lat,
                                             @PathVariable("long") double lng,
                                             @PathVariable("distance") double distance) throws RouteServiceException {
        return routeService.findRoutesByDefinition_Origin_LocationNear(new Point(lat, lng), new Distance(distance, Metrics.KILOMETERS));
    }*/

    /*@GetMapping("/location/{lat}/{long}/{lat1}/{long1}/{distance}")
    public List<RouteDto> getRoutesNearLocationsSimple(@PathVariable("lat") double lat,
                                              @PathVariable("long") double lng,
                                              @PathVariable("lat1") double lat1,
                                              @PathVariable("long1") double lng1,
                                              @PathVariable("distance") double d) throws RouteServiceException {
        Point origin = new Point(lat, lng);
        Point destination = new Point(lat1, lng1);
        Distance distance = new Distance(d, Metrics.KILOMETERS);
        return routeService.findRoutesNearLocationsSimple(origin, destination, distance);
    }*/

    @GetMapping("/location/near/simple")
    public List<RouteDto> getRoutesNearLocationsSimple(@RequestParam String originLat, @RequestParam String originLng,
                                                       @RequestParam String destinationLat, @RequestParam String destinationLng,
                                                       @RequestParam String distance) {

        Point origin = new Point(Double.parseDouble(originLat), Double.parseDouble(originLng));
        Point destination = new Point(Double.parseDouble(destinationLat), Double.parseDouble(destinationLng));
        Distance distanceNearPoints = new Distance(Double.parseDouble(distance), Metrics.KILOMETERS);
        SearchCriteria searchCriteria = new SearchCriteria(origin, destination, distanceNearPoints);
        return routeService.findRoutesNearLocationsSimple(searchCriteria);
    }

    @GetMapping("/location/near/advanced")
    public List<RouteDto> getRoutesNearLocationsAdvanced(@RequestParam String originLat, @RequestParam String originLng,
                                                         @RequestParam String destinationLat, @RequestParam String destinationLng,
                                                         @RequestParam String minDepartureTime, @RequestParam String maxDepartureTime,
                                                         @RequestParam String gender, @RequestParam String smoker, @RequestParam String distance) {
        Point origin = new Point(Double.parseDouble(originLat), Double.parseDouble(originLng));
        Point destination = new Point(Double.parseDouble(destinationLat), Double.parseDouble(destinationLng));
        Distance distanceNearPoints = new Distance(Double.parseDouble(distance), Metrics.KILOMETERS);
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        LocalDateTime minDeparture = LocalDateTime.parse(minDepartureTime, formatter);
        LocalDateTime maxDeparture = LocalDateTime.parse(maxDepartureTime, formatter);
        SearchCriteriaAcceptanceType genderType = acceptanceTypeAssigner(Integer.parseInt(gender));
        SearchCriteriaAcceptanceType smokerType = acceptanceTypeAssigner(Integer.parseInt(smoker));
        SearchCriteria searchCriteria = new SearchCriteria(origin, destination, distanceNearPoints, minDeparture, maxDeparture, genderType, smokerType);

        log.info("ORIGIN: " + origin.toString());
        log.info("DESTINATION: " + destination.toString());
        log.info("DISTANCE: " + distanceNearPoints);
        log.info("MIN DEPARTURE: " + minDeparture);
        log.info("MAX DEPARTURE: " + maxDeparture);
        log.info("GENDERTYPE: " + genderType);
        log.info("SMOKERTYPE: " + smokerType);
        return routeService.findRoutesNearLocationsAdvanced(searchCriteria);
    }

    private SearchCriteriaAcceptanceType acceptanceTypeAssigner(int data) {
        switch (data) {
            case 0:
                return SearchCriteriaAcceptanceType.YES;
            case 1:
                return SearchCriteriaAcceptanceType.NO;
            default:
                return SearchCriteriaAcceptanceType.EITHER;
        }
    }
}
