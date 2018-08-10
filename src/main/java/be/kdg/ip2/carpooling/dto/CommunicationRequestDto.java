package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationRequestDto {
    private String id;
    private String routeId;
    private String userId;
    private RouteLocationDto origin;
    private RouteLocationDto destination;
    private String comment;
    private CommunicationRequestStatus requestStatus;

    public CommunicationRequestDto(String routeId, String userId, RouteLocationDto origin, RouteLocationDto destination, String comment, CommunicationRequestStatus requestStatus) {
        this.routeId = routeId;
        this.userId = userId;
        this.origin = origin;
        this.destination = destination;
        this.comment = comment;
        this.requestStatus = requestStatus;
    }

    public CommunicationRequestDto(CommunicationRequest request) {
        this.id = request.getId();
        this.routeId = request.getRouteId();
        this.userId = request.getUserId();
        this.origin = new RouteLocationDto(request.getOrigin().getLocationName(), request.getOrigin().getLocation().getX(), request.getOrigin().getLocation().getY());
        this.destination = new RouteLocationDto(request.getDestination().getLocationName(), request.getDestination().getLocation().getX(), request.getDestination().getLocation().getY());
        this.comment = request.getComment();
        this.requestStatus = request.getRequestStatus();
    }
}
