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

    public final SimplePath<RouteLocation> destination = createSimple("destination", RouteLocation.class);

    public final SimplePath<RouteLocation> origin = createSimple("origin", RouteLocation.class);

    public final EnumPath<RouteType> routeType = createEnum("routeType", RouteType.class);

    public final ListPath<RouteLocation, SimplePath<RouteLocation>> waypoints = this.<RouteLocation, SimplePath<RouteLocation>>createList("waypoints", RouteLocation.class, SimplePath.class, PathInits.DIRECT2);

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

