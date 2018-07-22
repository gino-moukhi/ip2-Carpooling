package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteDefinition;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.route.RouteType;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.repository.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RouteRepoTest {
    @Autowired
    private RouteRepository repo;

    @Test
    public void testDbSeeder() {
        assertEquals(2, repo.findAll().size());
        Route route1 = repo.findRouteByDefinition_Start_LocationNameAndDefinition_Finish_LocationName(
                "Brugstraat 103, Brecht", "Wilgendaalstraat 15, Schoten"
        );
        Route route2 = repo.findRouteByDefinition_Start_LocationNameAndDefinition_Finish_LocationName(
                "Eikenlei 8, Brecht", "Groenplaats, Antwerpen"
        );
//        assertThat(route1.getDefinition().getStart().getLat(), equalTo(51.297413));
//        assertThat(route1.getDefinition().getStart().getLng(), equalTo(4.57358));
//        assertThat(route1.getDefinition().getFinish().getLat(), equalTo(51.253799));
//        assertThat(route1.getDefinition().getFinish().getLng(), equalTo(4.495248));
        assertThat(route1.getDefinition().getStart().getLocation().getX(), equalTo(51.297413));
        assertThat(route1.getDefinition().getStart().getLocation().getY(), equalTo(4.57358));
        assertThat(route1.getDefinition().getFinish().getLocation().getX(), equalTo(51.253799));
        assertThat(route1.getDefinition().getFinish().getLocation().getY(), equalTo(4.495248));

        assertThat(route1.getVehicleType(), equalTo(VehicleType.SEDAN));
        assertThat(route1.getAvailablePassengers(), equalTo(3));

//        assertThat(route2.getDefinition().getStart().getLat(), equalTo(51.303687));
//        assertThat(route2.getDefinition().getStart().getLng(), equalTo(4.566821));
//        assertThat(route2.getDefinition().getFinish().getLat(), equalTo(51.218962));
//        assertThat(route2.getDefinition().getFinish().getLng(), equalTo(4.402153));
        assertThat(route2.getDefinition().getStart().getLocation().getX(), equalTo(51.303687));
        assertThat(route2.getDefinition().getStart().getLocation().getY(), equalTo(4.566821));
        assertThat(route2.getDefinition().getFinish().getLocation().getX(), equalTo(51.218962));
        assertThat(route2.getDefinition().getFinish().getLocation().getY(), equalTo(4.402153));
        assertThat(route2.getDefinition().getWaypoints().size(), equalTo(2));
        assertThat(route2.getVehicleType(), equalTo(VehicleType.SUV));
        assertThat(route2.getAvailablePassengers(), equalTo(4));
    }

    @Test
    public void testRemoveRoute() {
        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        Route route1 = allRoutes.get(0);
        repo.deleteById(route1.getId());
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(1));
        assertNotEquals(allRoutes.get(0).getId(), route1.getId());
    }

    @Test
    public void testRemoveAll() {
        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        repo.deleteAll();
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(0));
    }

    @Test
    public void testAddRoute() {
        List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
        //waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", 51.297413, 4.573580));
        waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", new GeoJsonPoint(51.297413, 4.573580)));
        LocalDateTime timestamp = LocalDateTime.now();

        Route route1 = new Route(new RouteDefinition(
                //new RouteLocation("Kerklei 69, Brecht", 51.293054, 4.573580),
                new RouteLocation("Kerklei 69, Brecht", new GeoJsonPoint(51.293054, 4.573580)),
                //new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.584703),
                new RouteLocation("Wilgendaalstraat 15, Schoten", new GeoJsonPoint(51.253799, 4.584703)),
                RouteType.SINGLE,
                waypointsForRoute1),
                VehicleType.SEDAN,
                timestamp,
                3);

        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        assertNull(repo.findRouteByDefinition_StartAndDefinition_Finish(route1.getDefinition().getStart(), route1.getDefinition().getFinish()));
        repo.insert(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
        assertNotNull(repo.findRouteByDefinition_StartAndDefinition_Finish(route1.getDefinition().getStart(), route1.getDefinition().getFinish()));
    }

    @Test
    public void testAddDuplicateRoute() {
        List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
        //waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", 51.297413, 4.573580));
        waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", new GeoJsonPoint(51.297413, 4.573580)));
        LocalDateTime timestamp = LocalDateTime.now();

        Route route1 = new Route(new RouteDefinition(
                //new RouteLocation("Kerklei 69, Brecht", 51.293054, 4.573580),
                new RouteLocation("Kerklei 69, Brecht", new GeoJsonPoint(51.293054, 4.573580)),
                //new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.584703),
                new RouteLocation("Wilgendaalstraat 15, Schoten", new GeoJsonPoint(51.253799, 4.584703)),
                RouteType.SINGLE,
                waypointsForRoute1),
                VehicleType.SEDAN,
                timestamp,
                3);

        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        assertNull(repo.findRouteByDefinition_StartAndDefinition_Finish(route1.getDefinition().getStart(), route1.getDefinition().getFinish()));
        repo.insert(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
        Route found = repo.findRouteByDefinition_StartAndDefinition_Finish(route1.getDefinition().getStart(), route1.getDefinition().getFinish());
        assertNotNull(found);
        route1.setId(found.getId());
        repo.save(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
    }

    @Test
    public void testUpdateRoute() {
        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        Route route1 = allRoutes.get(0);
        route1.setVehicleType(VehicleType.CONVERTIBLE);
        route1.setAvailablePassengers(1);
        route1.getDefinition().getStart().setLocationName("Brugstraat 103, Sint-Job");
        route1.getDefinition().setType(RouteType.SINGLE);
        repo.save(route1);
        Route updatedRoute = repo.findRouteById(route1.getId());
        assertThat(updatedRoute.getVehicleType(), equalTo(VehicleType.CONVERTIBLE));
        assertThat(updatedRoute.getAvailablePassengers(), equalTo(1));
        assertThat(updatedRoute.getDefinition().getStart().getLocationName(), equalTo("Brugstraat 103, Sint-Job"));
        assertThat(updatedRoute.getDefinition().getType(), equalTo(RouteType.SINGLE));
    }
}
