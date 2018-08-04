package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.domain.route.Place;
import be.kdg.ip2.carpooling.domain.route.SourceType;
import be.kdg.ip2.carpooling.repository.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PlaceRepoTest {

    @Autowired
    private PlaceRepository placeRepo;

    @Before
    public void setUp() {
        placeRepo.deleteAll();
        Place origin = new Place("Brugstraat 103, 2960 Brecht, België", new GeoJsonPoint(51.2974128, 4.573580099999958), SourceType.ORIGIN);
        Place destination = new Place("Groenplaats, 2000 Antwerpen, België", new GeoJsonPoint(51.2189619, 4.402152900000033), SourceType.DESTINATION);
        Place wp1 = new Place("Wilgendaalstraat, 2900 Schoten, België", new GeoJsonPoint(51.253992, 4.494731300000012), SourceType.WAYPOINT);
        Place wp2 = new Place("Jan Van Puyenbroecklaan, 2900 Schoten, België", new GeoJsonPoint(51.2617724, 4.467314299999998), SourceType.WAYPOINT);
        Place wp3 = new Place("Churchilllaan, 2900 Schoten, België", new GeoJsonPoint(51.24990390000001, 4.487032699999986), SourceType.WAYPOINT);
        Place wp4 = new Place("Ter Heydelaan 418, 2100 Antwerpen, België", new GeoJsonPoint(51.2313514, 4.477223600000002), SourceType.WAYPOINT);
        List<Place> places = Arrays.asList(origin, destination, wp1, wp2, wp3, wp4);
        placeRepo.insert(places);
    }

    @Test
    public void testGeoSpatialQueryForSingleElement() {
        List<Place> places = placeRepo.findAll();
        assertThat(places.size(), equalTo(6));
        List<Place> geoSpatialQueryResults = placeRepo.findPlacesByLocationNear(new Point(51.303687, 4.566821), new Distance(2, Metrics.KILOMETERS));
        assertThat(geoSpatialQueryResults.size(), equalTo(1));
        assertThat(geoSpatialQueryResults.get(0).getLocationName(), equalTo("Brugstraat 103, 2960 Brecht, België"));
    }

    @Test
    public void testGeoSpatialQueryForMultipleElements() {
        List<Place> places = placeRepo.findAll();
        Place p1 = places.get(2);
        Place p2 = places.get(3);
        Place p3 = places.get(4);
        assertThat(places.size(), equalTo(6));
        List<Place> geoSpatialQueryResults = placeRepo.findPlacesByLocationNear(new Point(51.258963, 4.483490), new Distance(3, Metrics.KILOMETERS));
        assertThat(geoSpatialQueryResults.size(), equalTo(3));
        log.info(geoSpatialQueryResults.toString());
        log.info(p1.toString());
        log.info(p2.toString());
        log.info(p3.toString());
        /*assertTrue(geoSpatialQueryResults.contains(p3));
        assertTrue(geoSpatialQueryResults.contains(p1));
        assertTrue(geoSpatialQueryResults.contains(p2));*/
        assertEquals(geoSpatialQueryResults.get(0).toString(),p3.toString());
        assertEquals(geoSpatialQueryResults.get(1).toString(),p1.toString());
        assertEquals(geoSpatialQueryResults.get(2).toString(),p2.toString());
    }
}
