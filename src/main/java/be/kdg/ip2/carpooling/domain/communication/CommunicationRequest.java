package be.kdg.ip2.carpooling.domain.communication;

import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.domain.user.RouteUser;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "communicationRequests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunicationRequest {
    @Id
    private String id;
    private String routeId;
    private RouteUser user;
    private RouteLocation origin;
    private RouteLocation destination;
    private String comment;
    private CommunicationRequestStatus requestStatus;

    public CommunicationRequest(String routeId, RouteUser user, RouteLocation origin, RouteLocation destination, String comment, CommunicationRequestStatus requestStatus) {
        this.routeId = routeId;
        this.user = user;
        this.origin = origin;
        this.destination = destination;
        this.comment = comment;
        this.requestStatus = requestStatus;
    }

    public CommunicationRequest(CommunicationRequestDto dto) {
        this.routeId = dto.getRouteId();
        this.user = new RouteUser(dto.getUser());
        this.origin = new RouteLocation(dto.getOrigin().getName(), new Point(dto.getOrigin().getLat(), dto.getOrigin().getLng()));
        this.destination = new RouteLocation(dto.getDestination().getName(), new Point(dto.getDestination().getLat(), dto.getDestination().getLng()));
        this.comment = dto.getComment();
        this.requestStatus = dto.getRequestStatus();
    }
}
