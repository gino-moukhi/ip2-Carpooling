package be.kdg.ip2.carpooling.util;

import be.kdg.ip2.carpooling.domain.route.Route;
import org.springframework.data.geo.Point;

import java.math.BigDecimal;

public class DecimalUtil {
    public static double round(double val, int numberOfDigits) {
        BigDecimal bigDecimal = new BigDecimal(val);
        bigDecimal = bigDecimal.setScale(numberOfDigits, BigDecimal.ROUND_HALF_EVEN);
        return bigDecimal.doubleValue();
    }

    public static double roundForPoint(double val) {
        BigDecimal bigDecimal = new BigDecimal(val);
        bigDecimal = bigDecimal.setScale(6, BigDecimal.ROUND_HALF_EVEN);
        return bigDecimal.doubleValue();
    }

    public static Route roundLocationPoints(Route route) {
        route.getDefinition().getOrigin().setLocation(new Point(
                roundForPoint(route.getDefinition().getOrigin().getLocation().getX()),
                roundForPoint(route.getDefinition().getOrigin().getLocation().getY())));
        route.getDefinition().getDestination().setLocation(new Point(
                roundForPoint(route.getDefinition().getDestination().getLocation().getX()),
                roundForPoint(route.getDefinition().getDestination().getLocation().getY())));
        route.getDefinition().getWaypoints().forEach(wp -> wp.setLocation(new Point(
                roundForPoint(wp.getLocation().getX()),
                roundForPoint(wp.getLocation().getY()))));
        return route;
    }
}
