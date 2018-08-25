package be.kdg.ip2.carpooling.domain.route;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteLocation is a Querydsl query type for RouteLocation
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRouteLocation extends BeanPath<RouteLocation> {

    private static final long serialVersionUID = -1978616963L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteLocation routeLocation = new QRouteLocation("routeLocation");

    public final org.springframework.data.geo.QPoint location;

    public final StringPath locationName = createString("locationName");

    public QRouteLocation(String variable) {
        this(RouteLocation.class, forVariable(variable), INITS);
    }

    public QRouteLocation(Path<? extends RouteLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteLocation(PathMetadata metadata, PathInits inits) {
        this(RouteLocation.class, metadata, inits);
    }

    public QRouteLocation(Class<? extends RouteLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new org.springframework.data.geo.QPoint(forProperty("location")) : null;
    }

}

