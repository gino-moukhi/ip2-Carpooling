package be.kdg.ip2.carpooling;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteDefinition;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.route.RouteType;
import be.kdg.ip2.carpooling.domain.user.*;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import be.kdg.ip2.carpooling.service.user.UserService;
import be.kdg.ip2.carpooling.service.user.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DbSeeder implements CommandLineRunner {
    private UserService userService;
    private RouteService routeService;

    @Autowired
    public DbSeeder(UserService userService, RouteService routeService) {
        this.userService = userService;
        this.routeService = routeService;
    }

    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createRoutes();
    }

    private void createUsers() {
        User gino = new User("gino.moukhi@student.kdg.be", "Snor123",
                new Name("Gino", "Moukhi"),
                new Address("Brugstraat", 103, 2960, "Brecht"),
                21, Gender.MALE, false,
                new Vehicle("Mercedes", VehicleType.SEDAN, 4.2, 3));

        User jimmy = new User("jimmy.kotton@gmail.com", "Gigedy",
                new Name("Jimmy", "Kotton"),
                new Address("Eikenlei", 8, 2960, "Brecht"),
                25, Gender.MALE, true,
                new Vehicle("Dodge", VehicleType.SUV, 8.0, 4));

        User sophie = new User("sophie.kotton@gmail.com", "Barbie",
                new Name("Sophie", "Kotton"),
                new Address("Wilgendaalstraat", 15, 2900, "Schoten"),
                27, Gender.FEMALE, true,
                new Vehicle("Toyota", VehicleType.HATCHBACK, 5.1, 3));

        // DELETE
        //userService.deleteAll();
        // INSERT OR UPDATE (depends on the fact that the users exists in the collection or not)
        List<User> users = Arrays.asList(gino, jimmy, sophie);
        users.forEach(user -> {
            try {
                userService.saveWithCheck(user, false);
            } catch (UserServiceException e) {
                log.error("Something went wrong when calling the SaveWithCheck function in the userService " + e);
            }
        });
    }

    private void createRoutes() {
        List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
        List<RouteLocation> waypointsForRoute2 = new ArrayList<>();
        LocalDateTime timestamp = LocalDateTime.now();
        waypointsForRoute1.add(new RouteLocation("Eikenlei 8, Brecht", 51.303687, 4.566821));

        Route route1 = new Route(new RouteDefinition(
                new RouteLocation("Brugstraat 103, Brecht", 51.297413, 4.573580),
                new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.495248),
                RouteType.RETURN,
                waypointsForRoute1),
                VehicleType.SEDAN,
                timestamp,
                3
        );
        waypointsForRoute2.add(new RouteLocation("Brugstraat 103, Brecht", 51.297413, 4.573580));
        waypointsForRoute2.add(new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.495248));
        timestamp = LocalDateTime.of(2018,7,28,18,0);

        Route route2 = new Route(new RouteDefinition(
                new RouteLocation("Eikenlei 8, Brecht", 51.303687, 4.566821),
                new RouteLocation("Groenplaats, Antwerpen", 51.218962, 4.402153),
                RouteType.SINGLE,
                waypointsForRoute2),
                VehicleType.SUV,
                timestamp,
                4
        );

        //DELETE
        //routeService.deleteAll();

        List<Route> routes = Arrays.asList(route1, route2);
        routes.forEach(route -> {
            try {
                routeService.saveWithCheck(route,false);
            } catch (RouteServiceException e) {
                log.error("Something went wrong when calling the addRoute function in the routeService " + e);
            }
        });
    }
}
