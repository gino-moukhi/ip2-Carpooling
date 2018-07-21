package be.kdg.ip2.carpooling.domain.route;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoute is a Querydsl query type for Route
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoute extends EntityPathBase<Route> {

    private static final long serialVersionUID = 1388631976L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoute route = new QRoute("route");

    public final NumberPath<Integer> availablePassengers = createNumber("availablePassengers", Integer.class);

    public final QRouteDefinition definition;

    public final DateTimePath<java.time.LocalDateTime> departure = createDateTime("departure", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final EnumPath<be.kdg.ip2.carpooling.domain.user.VehicleType> vehicleType = createEnum("vehicleType", be.kdg.ip2.carpooling.domain.user.VehicleType.class);

    public QRoute(String variable) {
        this(Route.class, forVariable(variable), INITS);
    }

    public QRoute(Path<? extends Route> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoute(PathMetadata metadata, PathInits inits) {
        this(Route.class, metadata, inits);
    }

    public QRoute(Class<? extends Route> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.definition = inits.isInitialized("definition") ? new QRouteDefinition(forProperty("definition")) : null;
    }

}

