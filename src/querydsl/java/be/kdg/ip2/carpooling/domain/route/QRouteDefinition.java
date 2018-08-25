package be.kdg.ip2.carpooling.domain.route;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteDefinition is a Querydsl query type for RouteDefinition
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRouteDefinition extends BeanPath<RouteDefinition> {

    private static final long serialVersionUID = -1336641765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteDefinition routeDefinition = new QRouteDefinition("routeDefinition");

    public final QRouteLocation destination;

    public final QRouteLocation origin;

    public final EnumPath<RouteType> routeType = createEnum("routeType", RouteType.class);

    public final ListPath<RouteLocation, QRouteLocation> waypoints = this.<RouteLocation, QRouteLocation>createList("waypoints", RouteLocation.class, QRouteLocation.class, PathInits.DIRECT2);

    public QRouteDefinition(String variable) {
        this(RouteDefinition.class, forVariable(variable), INITS);
    }

    public QRouteDefinition(Path<? extends RouteDefinition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteDefinition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteDefinition(PathMetadata metadata, PathInits inits) {
        this(RouteDefinition.class, metadata, inits);
    }

    public QRouteDefinition(Class<? extends RouteDefinition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.destination = inits.isInitialized("destination") ? new QRouteLocation(forProperty("destination"), inits.get("destination")) : null;
        this.origin = inits.isInitialized("origin") ? new QRouteLocation(forProperty("origin"), inits.get("origin")) : null;
    }

}

