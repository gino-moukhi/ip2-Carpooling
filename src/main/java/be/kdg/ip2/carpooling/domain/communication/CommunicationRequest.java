package be.kdg.ip2.carpooling.domain.communication;

import be.kdg.ip2.carpooling.domain.route.RouteLocation;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "communicationRequests")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationRequest implements Comparable<CommunicationRequest> {
    @Id
    private String id;
    private String routeId;
    private String userId;
    private RouteLocation origin;
    private RouteLocation destination;
    private String comment;
    private CommunicationRequestStatus requestStatus;

    public CommunicationRequest(String routeId, String userId, RouteLocation origin, RouteLocation destination, String comment, CommunicationRequestStatus requestStatus) {
        this.routeId = routeId;
        this.userId = userId;
        this.origin = origin;
        this.destination = destination;
        this.comment = comment;
        this.requestStatus = requestStatus;
    }

    public CommunicationRequest(CommunicationRequestDto dto) {
        this.routeId = dto.getRouteId();
        this.userId = dto.getUserId();
        this.origin = new RouteLocation(dto.getOrigin().getName(), new Point(dto.getOrigin().getLat(), dto.getOrigin().getLng()));
        this.destination = new RouteLocation(dto.getDestination().getName(), new Point(dto.getDestination().getLat(), dto.getDestination().getLng()));
        this.comment = dto.getComment();
        this.requestStatus = dto.getRequestStatus();
    }

    @Override
    public int compareTo(CommunicationRequest cr) {
        int i;
        i = routeId.compareTo(cr.getRouteId());
        if (i != 0) return i;
        i = userId.compareTo(cr.getUserId());
        if (i != 0) return i;
        i = origin.compareTo(cr.getOrigin());
        if (i != 0) return i;
        i = destination.compareTo(cr.getDestination());
        if (i != 0) return i;
        i = comment.compareTo(cr.getComment());
        if (i != 0) return i;
        i = requestStatus.compareTo(cr.getRequestStatus());
        return i;
    }
}
