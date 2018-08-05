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

    public static final QRouteDefinition routeDefinition = new QRouteDefinition("routeDefinition");

    public final ComparablePath<RouteLocation> destination = createComparable("destination", RouteLocation.class);

    public final ComparablePath<RouteLocation> origin = createComparable("origin", RouteLocation.class);

    public final EnumPath<RouteType> routeType = createEnum("routeType", RouteType.class);

    public final ListPath<RouteLocation, ComparablePath<RouteLocation>> waypoints = this.<RouteLocation, ComparablePath<RouteLocation>>createList("waypoints", RouteLocation.class, ComparablePath.class, PathInits.DIRECT2);

    public QRouteDefinition(String variable) {
        super(RouteDefinition.class, forVariable(variable));
    }

    public QRouteDefinition(Path<? extends RouteDefinition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRouteDefinition(PathMetadata metadata) {
        super(RouteDefinition.class, metadata);
    }

}

