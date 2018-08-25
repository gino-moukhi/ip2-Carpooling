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
        assertEquals(rl1, rl2);
        assertNotEquals(rl1, rl3);
    }

    @Test
    public void testRouteUserComparing() {
        RouteUser routeUser0 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser1 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser2 = new RouteUser("54321", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser3 = new RouteUser("12345", "my.mayl@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser4 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "FullName"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser5 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 33, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser6 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.FEMALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser7 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                true, new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser8 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Automobile", VehicleType.SUV, 4.2, 4));
        RouteUser routeUser9 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.CONVERTIBLE, 4.2, 4));
        RouteUser routeUser10 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 8.0, 4));
        RouteUser routeUser11 = new RouteUser("12345", "my.mail@mail.ru", new Name("My", "Name"), 23, Gender.MALE,
                false, new Vehicle("Car", VehicleType.SUV, 4.2, 2));

        User user0 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user1 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user2 = new User("my.mayl@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user3 = new User("my.mail@mail.ru", "MyPassworD", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user4 = new User("my.mail@mail.ru", "MyPassword", new Name("Mi", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user5 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "NamE"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user6 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("street", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user7 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 5, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user8 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 4321, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user9 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Work"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user10 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 33, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user11 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.FEMALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user12 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, true,
                new Vehicle("Car", VehicleType.SUV, 4.2, 4));
        User user13 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Automobile", VehicleType.SUV, 4.2, 4));
        User user14 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.COUPE, 4.2, 4));
        User user15 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 2.2, 4));
        User user16 = new User("my.mail@mail.ru", "MyPassword", new Name("My", "Name"),
                new Address("streetName", 1, 1234, "Home"), 23, Gender.MALE, false,
                new Vehicle("Car", VehicleType.SUV, 4.2, 3));

        user0.setId("12345");
        user1.setId("12345");
        user2.setId("54321");
        user3.setId("12345");
        user4.setId("12345");
        user5.setId("12345");
        user6.setId("12345");
        user7.setId("12345");
        user8.setId("12345");
        user9.setId("12345");
        user10.setId("12345");
        user11.setId("12345");
        user12.setId("12345");
        user13.setId("12345");
        user14.setId("12345");
        user15.setId("12345");
        user16.setId("12345");

        assertEquals(routeUser0, routeUser1);
        assertNotEquals(routeUser0, routeUser2);
        assertNotEquals(routeUser0, routeUser3);
        assertNotEquals(routeUser0, routeUser4);
        assertNotEquals(routeUser0, routeUser5);
        assertNotEquals(routeUser0, routeUser6);
        assertNotEquals(routeUser0, routeUser7);
        assertNotEquals(routeUser0, routeUser8);
        assertNotEquals(routeUser0, routeUser9);
        assertNotEquals(routeUser0, routeUser10);
        assertNotEquals(routeUser0, routeUser11);

        assertEquals(user0, user1);
        assertNotEquals(user0, user2);
        assertNotEquals(user0, user3);
        assertNotEquals(user0, user4);
        assertNotEquals(user0, user5);
        assertNotEquals(user0, user6);
        assertNotEquals(user0, user7);
        assertNotEquals(user0, user8);
        assertNotEquals(user0, user9);
        assertNotEquals(user0, user10);
        assertNotEquals(user0, user11);
        assertNotEquals(user0, user12);
        assertNotEquals(user0, user13);
        assertNotEquals(user0, user14);
        assertNotEquals(user0, user15);
        assertNotEquals(user0, user16);

        assertThat(routeUser0.compareTo(user0), equalTo(0));
        assertThat(routeUser0.compareTo(user2), not(0));
        assertThat(routeUser0.compareTo(user3), equalTo(0));
        assertThat(routeUser0.compareTo(user4), not(0));
        assertThat(routeUser0.compareTo(user5), not(0));
        assertThat(routeUser0.compareTo(user6), equalTo(0));
        assertThat(routeUser0.compareTo(user7), equalTo(0));
        assertThat(routeUser0.compareTo(user8), equalTo(0));
        assertThat(routeUser0.compareTo(user9), equalTo(0));
        assertThat(routeUser0.compareTo(user10), not(0));
        assertThat(routeUser0.compareTo(user11), not(0));
        assertThat(routeUser0.compareTo(user12), not(0));
        assertThat(routeUser0.compareTo(user13), not(0));
        assertThat(routeUser0.compareTo(user14), not(0));
        assertThat(routeUser0.compareTo(user15), not(0));
        assertThat(routeUser0.compareTo(user16), not(0));
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

        assertEquals(rd1, rd2);
        assertNotEquals(rd1, rd3);
        assertNotEquals(rd1, rd4);
        assertNotEquals(rd1, rd5);


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
        assertEquals(rd6, rd7);
        assertNotEquals(rd6, rd8);
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

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, r4);
        assertNotEquals(r1, r5);
        assertNotEquals(r1, r6);
        assertNotEquals(r1, r7);
        Set<Route> found = new TreeSet<>();
        routes.forEach(route -> {
            if (route.equals(r2)) {
                found.add(route);
            }
        });
        assertThat(found.size(), equalTo(1));
    }
}
