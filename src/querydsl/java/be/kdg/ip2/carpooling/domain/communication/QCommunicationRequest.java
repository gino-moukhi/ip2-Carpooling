package be.kdg.ip2.carpooling.domain.communication;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunicationRequest is a Querydsl query type for CommunicationRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommunicationRequest extends EntityPathBase<CommunicationRequest> {

    private static final long serialVersionUID = 25681709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunicationRequest communicationRequest = new QCommunicationRequest("communicationRequest");

    public final StringPath comment = createString("comment");

    public final be.kdg.ip2.carpooling.domain.route.QRouteLocation destination;

    public final StringPath id = createString("id");

    public final be.kdg.ip2.carpooling.domain.route.QRouteLocation origin;

    public final EnumPath<CommunicationRequestStatus> requestStatus = createEnum("requestStatus", CommunicationRequestStatus.class);

    public final StringPath routeId = createString("routeId");

    public final StringPath userId = createString("userId");

    public QCommunicationRequest(String variable) {
        this(CommunicationRequest.class, forVariable(variable), INITS);
    }

    public QCommunicationRequest(Path<? extends CommunicationRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunicationRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunicationRequest(PathMetadata metadata, PathInits inits) {
        this(CommunicationRequest.class, metadata, inits);
    }

    public QCommunicationRequest(Class<? extends CommunicationRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.destination = inits.isInitialized("destination") ? new be.kdg.ip2.carpooling.domain.route.QRouteLocation(forProperty("destination"), inits.get("destination")) : null;
        this.origin = inits.isInitialized("origin") ? new be.kdg.ip2.carpooling.domain.route.QRouteLocation(forProperty("origin"), inits.get("origin")) : null;
    }

}

