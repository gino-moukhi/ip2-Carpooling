package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.place.SourceType;
import be.kdg.ip2.carpooling.domain.route.*;
import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.repository.route.RouteRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

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
        Point brugstraat = new Point(51.297413, 4.57358);
        Point heydelaan = new Point(51.2313514, 4.477223600000002);
        Point wilgendaal = new Point(51.253799, 4.495248);
        Point churchillaan = new Point(51.249904, 4.487033);
        Point groenplaats = new Point(51.218962, 4.402153);
        String sophieId = "5b534318d3303d2c7090d940";
        String ginoId = "5b534318d3303d2c7090d93e";
        RouteLocation rl1 = new RouteLocation("Brugstraat 103, 2960 Brecht, België", brugstraat);
        RouteLocation rl2 = new RouteLocation("Churchilllaan, 2900 Schoten, België", churchillaan);
        RouteLocation rl3 = new RouteLocation("Ter Heydelaan 418, 2100 Antwerpen, België", new Point(51.231351, 4.477224));

        Set<RouteLocation> locations = new TreeSet<>(Arrays.asList(rl1, rl2, rl3));
        log.info(locations.toString());

        Set<Route> uniqueRoutes = checkForRoutesInRepo(rl1, rl2);
        log.info(uniqueRoutes.toString());
        Query query = new Query();
        //query.addCriteria(Criteria.where("owner._id").is("5b534318d3303d2c7090d940").orOperator(Criteria.where("passengers").elemMatch(Criteria.where("_id").is("5b534318d3303d2c7090d940"))));
        /*query.addCriteria(Criteria.where("owner._id").is("5b534318d3303d2c7090d940"));
        query.addCriteria(Criteria.where("passengers").elemMatch(Criteria.where("_id").is("5b534318d3303d2c7090d940")));*/
        //query.addCriteria(new Criteria().orOperator(Criteria.where("owner._id").is(ginoId), Criteria.where("passengers").elemMatch(Criteria.where("_id").is(ginoId))));
        query.addCriteria(Criteria.where("owner.gender").is(Gender.FEMALE));
        List<Route> foundRoutes = mongoOperations.find(query, Route.class);
        List<Route> foundRoutes2 = new ArrayList<>();
        QRoute qRoute = new QRoute("route");
        BooleanExpression departureFilter = qRoute.departure.between(LocalDateTime.of(2018, 8, 6, 12, 40),
                LocalDateTime.of(2018, 8, 6, 12, 55));
        List<Route> allFilteredRoutes = (List<Route>) repo.findAll(departureFilter);
        allFilteredRoutes.forEach(route -> {
            if ((route.getDefinition().getOrigin().compareTo(rl2) == 0 ||
                    route.getDefinition().getWaypoints().contains(rl2)) &&
                    (route.getDefinition().getDestination().compareTo(rl3) == 0 ||
                    route.getDefinition().getWaypoints().contains(rl3))) {
                foundRoutes2.add(route);
            }
        });
        foundRoutes2.forEach(System.out::println);
    }

    private Set<Route> checkForRoutesInRepo(RouteLocation origin, RouteLocation destination) {
        List<Route> routesOriginDestination = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.DESTINATION, origin, destination);
        List<Route> routesOriginWaypoint = repo.findExistingRoutesFromPlaces(SourceType.ORIGIN, SourceType.WAYPOINT, origin, destination);
        List<Route> routesWaypointDestination = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.DESTINATION, origin, destination);
        List<Route> routesWaypointWaypoint = repo.findExistingRoutesFromPlaces(SourceType.WAYPOINT, SourceType.WAYPOINT, origin, destination);
        Set<Route> uniqueRoutes = new TreeSet<>();
        uniqueRoutes.addAll(routesOriginDestination);
        uniqueRoutes.addAll(routesOriginWaypoint);
        uniqueRoutes.addAll(routesWaypointDestination);
        uniqueRoutes.addAll(routesWaypointWaypoint);
        return uniqueRoutes;
    }

    @Test
    public void clearCommunicationRequestsTest() {
        Route route = repo.findRouteById("5b675815d3303d3a74c4a339");
        route.getCommunicationRequests().clear();
        repo.save(route);
    }
    @Test
    public void testDbSeeder() {
        /*assertEquals(2, repo.findAll().size());
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
        assertThat(route2.getAvailablePassengers(), equalTo(4));*/
    }

    @Test
    public void testRemoveRoute() {
        /*List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(2));
        Route route1 = allRoutes.get(0);
        repo.deleteById(route1.getId());
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(1));
        assertNotEquals(allRoutes.get(0).getId(), route1.getId());*/
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
        waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", new Point(51.297413, 4.573580)));
        LocalDateTime timestamp = LocalDateTime.now();

        Route route1 = new Route(new RouteDefinition(
                //new RouteLocation("Kerklei 69, Brecht", 51.293054, 4.573580),
                new RouteLocation("Kerklei 69, Brecht", new Point(51.293054, 4.573580)),
                //new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.584703),
                new RouteLocation("Wilgendaalstraat 15, Schoten", new Point(51.253799, 4.584703)),
                RouteType.SINGLE,
                waypointsForRoute1),
                VehicleType.SEDAN,
                timestamp,
                3);

        List<Route> allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(3));
        assertNull(repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination()));
        repo.insert(route1);
        allRoutes = repo.findAll();
        assertThat(allRoutes.size(), equalTo(4));
        assertNotNull(repo.findRouteByDefinition_OriginAndDefinition_Destination(route1.getDefinition().getOrigin(), route1.getDefinition().getDestination()));*/
    }

    @Test
    public void testAddDuplicateRoute() {
        /*List<RouteLocation> waypointsForRoute1 = new ArrayList<>();
        //waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", 51.297413, 4.573580));
        waypointsForRoute1.add(new RouteLocation("Brugstraat 103, Brecht", new Point(51.297413, 4.573580)));
        LocalDateTime timestamp = LocalDateTime.now();

        Route route1 = new Route(new RouteDefinition(
                //new RouteLocation("Kerklei 69, Brecht", 51.293054, 4.573580),
                new RouteLocation("Kerklei 69, Brecht", new Point(51.293054, 4.573580)),
                //new RouteLocation("Wilgendaalstraat 15, Schoten", 51.253799, 4.584703),
                new RouteLocation("Wilgendaalstraat 15, Schoten", new Point(51.253799, 4.584703)),
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
        /*List<Route> allRoutes = repo.findAll();
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
        assertThat(updatedRoute.getDefinition().getRouteType(), equalTo(RouteType.SINGLE));*/
    }
}
