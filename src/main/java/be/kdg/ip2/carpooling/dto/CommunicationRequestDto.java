package be.kdg.ip2.carpooling.dto;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunicationRequestDto {
    private String id;
    private String routeId;
    private RouteUserDto user;
    private RouteLocationDto origin;
    private RouteLocationDto destination;
    private String comment;
    private CommunicationRequestStatus requestStatus;

    public CommunicationRequestDto(String routeId, RouteUserDto user, RouteLocationDto origin, RouteLocationDto destination, String comment, CommunicationRequestStatus requestStatus) {
        this.routeId = routeId;
        this.user = user;
        this.origin = origin;
        this.destination = destination;
        this.comment = comment;
        this.requestStatus = requestStatus;
    }

    public CommunicationRequestDto(CommunicationRequest request) {
        this.id = request.getId();
        this.routeId = request.getRouteId();
        this.user = new RouteUserDto(request.getUser());
        this.origin = new RouteLocationDto(request.getOrigin().getLocationName(), request.getOrigin().getLocation().getX(), request.getOrigin().getLocation().getY());
        this.destination = new RouteLocationDto(request.getDestination().getLocationName(), request.getDestination().getLocation().getX(), request.getDestination().getLocation().getY());
        this.comment = request.getComment();
        this.requestStatus = request.getRequestStatus();
    }
}
