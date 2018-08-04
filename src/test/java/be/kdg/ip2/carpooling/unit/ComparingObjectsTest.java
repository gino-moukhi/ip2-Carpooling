package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.RouteDefinition;
import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.route.RouteType;
import be.kdg.ip2.carpooling.domain.user.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@Slf4j
public class ComparingObjectsTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testRouteLocationComparing() {
        RouteLocation rl1 = new RouteLocation("Route1", new GeoJsonPoint(11,22));
        RouteLocation rl2 = new RouteLocation("Route2", new GeoJsonPoint(11,22));
        RouteLocation rl3 = new RouteLocation("Route2", new GeoJsonPoint(22,11));
        assertThat(rl1.compareTo(rl2), equalTo(0));
        assertThat(rl1.compareTo(rl3), equalTo(-1));
    }

    @Test
    public void testRouteDefinitionComparing() {
        RouteDefinition rd1 = new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN);
        RouteDefinition rd2 = new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN);
        RouteDefinition rd3 = new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.SINGLE);
        RouteDefinition rd4 = new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(22,11)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN);
        RouteDefinition rd5 = new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(11,22)),RouteType.RETURN);

        assertThat(rd1.compareTo(rd2), equalTo(0));
        assertThat(rd1.compareTo(rd3), equalTo(-1));
        assertThat(rd1.compareTo(rd4), equalTo(-1));
        assertThat(rd1.compareTo(rd5), equalTo(-1));


        List<RouteLocation> waypoints = new ArrayList<>();
        waypoints.add(new RouteLocation("WP1", new GeoJsonPoint(44,55)));
        waypoints.add(new RouteLocation("WP2", new GeoJsonPoint(44,55)));
        waypoints.add(new RouteLocation("WP3", new GeoJsonPoint(44,55)));
        List<RouteLocation> waypoints2 = new ArrayList<>();
        waypoints2.add(new RouteLocation("WP1", new GeoJsonPoint(44,55)));
        waypoints2.add(new RouteLocation("WP2", new GeoJsonPoint(44,55)));
        waypoints2.add(new RouteLocation("WP3", new GeoJsonPoint(44,55)));
        List<RouteLocation> waypoints3 = new ArrayList<>();
        waypoints3.add(new RouteLocation("WP1", new GeoJsonPoint(55,44)));
        waypoints3.add(new RouteLocation("WP2", new GeoJsonPoint(44,55)));
        waypoints3.add(new RouteLocation("WP3", new GeoJsonPoint(44,55)));

        RouteDefinition rd6 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        RouteDefinition rd7 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        RouteDefinition rd8 = new RouteDefinition(rd2.getOrigin(), rd2.getDestination(), rd2.getRouteType());
        rd6.setWaypoints(waypoints);
        rd7.setWaypoints(waypoints2);
        rd8.setWaypoints(waypoints3);
        assertThat(rd6.compareTo(rd7), equalTo(0));
        assertThat(rd6.compareTo(rd8), equalTo(-1));
    }

    @Test
    public void testCompleteRouteComparing() {
        Route r1 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN),
                VehicleType.SEDAN, LocalDateTime.of(2018,8,4,12,0),2);
        Route r2 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN),
                VehicleType.SEDAN, LocalDateTime.of(2018,8,4,12,0),2);
        Route r3 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(22,11)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.SINGLE),
                VehicleType.SEDAN, LocalDateTime.of(2018,8,4,12,0),2);
        Route r4 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN),
                VehicleType.HATCHBACK, LocalDateTime.of(2018,8,4,12,0),2);
        Route r5 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN),
                VehicleType.SEDAN, LocalDateTime.of(2018,7,9,12,0),2);
        Route r6 = new Route(new RouteDefinition(new RouteLocation("Route1", new GeoJsonPoint(11,22)),
                new RouteLocation("Route2", new GeoJsonPoint(22,11)),RouteType.RETURN),
                VehicleType.SEDAN, LocalDateTime.of(2018,8,4,12,0),4);
        List<Route> routes = new ArrayList<>(Arrays.asList(r1, r3, r4, r5, r6, r1, r1));

        assertThat(r1.compareTo(r2), equalTo(0));
        assertThat(r1.compareTo(r3), equalTo(-1));
        assertThat(r1.compareTo(r4), equalTo(-1));
        assertThat(r1.compareTo(r5), equalTo(-1));
        assertThat(r1.compareTo(r6), equalTo(-1));
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
