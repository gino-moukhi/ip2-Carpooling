package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/routes")
public class RouteController {
    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<Route> getAll() throws RouteServiceException {
        return routeService.findAllUsers();
    }

    @GetMapping("/{id}")
    public Route getRoute(@PathVariable("id") String id) throws RouteServiceException {
        return routeService.findRouteById(id);
    }

    @PostMapping
    public Route insert(@RequestBody Route route) throws RouteServiceException {
        return routeService.addRoute(route);
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
}
