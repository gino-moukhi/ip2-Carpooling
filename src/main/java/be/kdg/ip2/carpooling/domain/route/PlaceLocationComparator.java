package be.kdg.ip2.carpooling.domain.route;

import java.util.Arrays;
import java.util.Comparator;

public class PlaceLocationComparator implements Comparator<Place> {
    @Override
    public int compare(Place p1, Place p2) {
        return Arrays.equals(p1.getLocation().getCoordinates().toArray(), p2.getLocation().getCoordinates().toArray()) ? 0 : -1;
    }
}
