package be.kdg.ip2.carpooling.service.route;

public class RouteServiceException extends Exception {
    public RouteServiceException(String message) {
        super(message);
    }

    public RouteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
