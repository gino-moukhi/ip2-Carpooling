package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteDefinition;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.route.RouteType;
import be.kdg.ip2.carpooling.domain.user.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@Slf4j
public class ComparingObjectsTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testRouteLocationComparing() {
        RouteLocation rl1 = new RouteLocation("Route1", new Point(11, 22));
        RouteLocation rl2 = new RouteLocation("Route2", new Point(11, 22));
        RouteLocation rl3 = new RouteLocation("Route2", new Point(22, 11));
        assertThat(rl1.compareTo(rl2), equalTo(0));
        assertThat(rl1.compareTo(rl3), not(0));
    }

    @Test
    public void testRouteDefinitionComparing() {
        RouteDefinition rd1 = new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN);
        RouteDefinition rd2 = new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN);
        RouteDefinition rd3 = new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.SINGLE);
        RouteDefinition rd4 = new RouteDefinition(new RouteLocation("Route1", new Point(22, 11)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN);
        RouteDefinition rd5 = new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(11, 22)), RouteType.RETURN);

        assertThat(rd1.compareTo(rd2), equalTo(0));
        assertThat(rd1.compareTo(rd3), not(0));
        assertThat(rd1.compareTo(rd4), not(0));
        assertThat(rd1.compareTo(rd5), not(0));


        List<RouteLocation> waypoints = new ArrayList<>();
        waypoints.add(new RouteLocation("WP1", new Point(44, 55)));
        waypoints.add(new RouteLocation("WP2", new Point(44, 55)));
        waypoints.add(new RouteLocation("WP3", new Point(44, 55)));
        List<RouteLocation> waypoints2 = new ArrayList<>();
        waypoints2.add(new RouteLocation("WP1", new Point(44, 55)));
        waypoints2.add(new RouteLocation("WP2", new Point(44, 55)));
        waypoints2.add(new RouteLocation("WP3", new Point(44, 55)));
        List<RouteLocation> waypoints3 = new ArrayList<>();
        waypoints3.add(new RouteLocation("WP1", new Point(55, 44)));
        waypoints3.add(new RouteLocation("WP2", new Point(44, 55)));
        waypoints3.add(new RouteLocation("WP3", new Point(44, 55)));

        RouteDefinition rd6 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        RouteDefinition rd7 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        RouteDefinition rd8 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        rd6.setWaypoints(waypoints);
        rd7.setWaypoints(waypoints2);
        rd8.setWaypoints(waypoints3);
        assertThat(rd6.compareTo(rd7), equalTo(0));
        assertThat(rd6.compareTo(rd8), not(0));
    }

    @Test
    public void testCompleteRouteComparing() {
        RouteUser ru1 = new RouteUser("gino.moukhi@student.kdg.be", new Name("Gino", "Moukhi"),
                21, Gender.MALE, false, new Vehicle("Mercedes", VehicleType.SEDAN, 4.2, 3));
        RouteUser ru2 = new RouteUser("gino.moukhi@student.kdg.be", new Name("Gino", "Moukhi"),
                21, Gender.MALE, false, new Vehicle("Mercedes", VehicleType.HATCHBACK, 4.2, 3));
        RouteUser ru3 = new RouteUser("jimmy.kotton@gmail.com", new Name("Jimmy", "Kotton"),
                25, Gender.MALE, true, new Vehicle("Dodge", VehicleType.SEDAN, 8, 4));

        Route r1 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 8, 4, 12, 0), 2, ru1);
        Route r2 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 8, 4, 12, 0), 2, ru1);
        Route r3 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(22, 11)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.SINGLE),
                LocalDateTime.of(2018, 8, 4, 12, 0), 2, ru1);
        Route r4 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 8, 4, 12, 0), 2, ru2);
        Route r5 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 7, 9, 12, 0), 2, ru1);
        Route r6 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 8, 4, 12, 0), 4, ru1);
        Route r7 = new Route(new RouteDefinition(new RouteLocation("Route1", new Point(11, 22)),
                new RouteLocation("Route2", new Point(22, 11)), RouteType.RETURN),
                LocalDateTime.of(2018, 8, 4, 12, 0), 2, ru3);
        List<Route> routes = new ArrayList<>(Arrays.asList(r1, r3, r4, r5, r6, r7, r1, r1));

        assertThat(r1.compareTo(r2), equalTo(0));
        assertThat(r1.compareTo(r3), not(0));
        assertThat(r1.compareTo(r4), not(0));
        assertThat(r1.compareTo(r5), not(0));
        assertThat(r1.compareTo(r6), not(0));
        assertThat(r1.compareTo(r7), not(0));
        Set<Route> found = new TreeSet<>();
        routes.forEach(route -> {
            int i = route.compareTo(r2);
            if (i == 0) {
                found.add(route);
            }
        });
        assertThat(found.size(), equalTo(1));
    }
}
