package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.route.*;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import be.kdg.ip2.carpooling.repository.route.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RouteRepoTest {
    @Autowired
    private RouteRepository repo;
    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void testMongoOperations() {
        assertTrue(mongoOperations.collectionExists(Route.class));
        List<IndexInfo> indexInfo = mongoOperations.indexOps(Route.class).getIndexInfo();
        Point eikenlei = new Point(51.3036871, 4.56682109999997);
        Point heydelaan = new Point(51.2313514, 4.477223600000002);
        Point wilgendaal = new Point(51.253799, 4.495248);
        RouteLocation rl1 = new RouteLocation("Road1", new GeoJsonPoint(1.5, 1.5));
        RouteLocation rl2 = new RouteLocation("Road2", new GeoJsonPoint(1.5, 1));
        RouteLocation rl3 = new RouteLocation("Road3", new GeoJsonPoint(1.5, 1.5));

        Set<RouteLocation> locations = new TreeSet<>(Arrays.asList(rl1, rl2, rl3));
        log.info(locations.toString());

        /*List<Route> routesOriginDestination = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.DESTINATION, eikenlei, wilgendaal);
        List<Route> routesOriginWaypoint = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.WAYPOINT, eikenlei, wilgendaal);
        List<Route> routesWaypointDestination = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.DESTINATION, eikenlei, wilgendaal);
        List<Route> routesWaypointWaypoint = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.WAYPOINT, eikenlei, wilgendaal);*/
        List<Route> routesOriginDestination = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.DESTINATION, eikenlei, heydelaan);
        List<Route> routesOriginWaypoint = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.WAYPOINT, eikenlei, heydelaan);
        List<Route> routesWaypointDestination = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.DESTINATION, eikenlei, heydelaan);
        List<Route> routesWaypointWaypoint = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.WAYPOINT, eikenlei, heydelaan);
        Set<Route> uniqueRoutes = new TreeSet<>();
        uniqueRoutes.addAll(routesOriginDestination);
        uniqueRoutes.addAll(routesOriginWaypoint);
        uniqueRoutes.addAll(routesWaypointDestination);
        uniqueRoutes.addAll(routesWaypointWaypoint);
        log.info(uniqueRoutes.toString());
    }

    @Test
    public void testDbSeeder() {
        assertEquals(2, repo.findAll().size());
        Route route1 = repo.findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(
                "Brugstraat 103, Brecht", "Wilgendaalstraat 15, Schoten"
        );
        Route route2 = repo.findRouteByDefinition_Origin_LocationNameAndDefinition_Destination_LocationName(
                "Eikenlei 8, Brecht", "Groenplaats, Antwerpen"
        );
        assertThat(route1.getDefinition().getOrigin().getLocation().getX(), equalTo(51.297413));
        assertThat(route1.getDefinition().getOrigin().getLocation().getY(), equalTo(4.57358));
        assertThat(route1.getDefinition().getDestination().getLocation().getX(), equalTo(51.253799));
        assertThat(route1.getDefinition().getDestination().getLocation().getY(), equalTo(4.495248));

        assertThat(route1.getVehicleType(), equalTo(VehicleType.SEDAN));
        assertThat(route1.getAvailablePassengers(), equalTo(3));

        assertThat(route2.getDefinition().getOrigin().getLocation().getX(), equalTo(51.303687));
        assertThat(route2.getDefinition().getOrigin().getLocation().getY(), equalTo(4.566821));
        assertThat(route2.getDefinition().getDestination().getLocation().getX(), equalTo(51.218962));
        assertThat(route2.getDefinition().getDestination().getLocation().getY(), equalTo(4.402153));
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
        /*List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        repo.deleteAll();
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(0));*/
    }

    @Test
    public void testAddRoute() {
        /*List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
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
        assertNull(repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination()));
        repo.insert(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
        assertNotNull(repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination()));*/
    }

    @Test
    public void testAddDuplicateRoute() {
        /*List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
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
        assertNull(repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination()));
        repo.insert(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
        Route found = repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination());
        assertNotNull(found);
        route1.setId(found.getId());
        repo.save(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));*/
    }

    @Test
    public void testUpdateRoute() {
        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        Route route1 = allRoutes.get(0);
        route1.setVehicleType(VehicleType.CONVERTIBLE);
        route1.setAvailablePassengers(1);
        route1.getDefinition().getOrigin().setLocationName("Brugstraat 103, Sint-Job");
        route1.getDefinition().setRouteType(RouteType.SINGLE);
        repo.save(route1);
        Route updatedRoute = repo.findRouteById(route1.getId());
        assertThat(updatedRoute.getVehicleType(), equalTo(VehicleType.CONVERTIBLE));
        assertThat(updatedRoute.getAvailablePassengers(), equalTo(1));
        assertThat(updatedRoute.getDefinition().getOrigin().getLocationName(), equalTo("Brugstraat 103, Sint-Job"));
        assertThat(updatedRoute.getDefinition().getRouteType(), equalTo(RouteType.SINGLE));
    }
}
