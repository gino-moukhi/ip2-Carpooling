package be.kdg.ip2.carpooling.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRouteUser is a Querydsl query type for RouteUser
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRouteUser extends BeanPath<RouteUser> {

    private static final long serialVersionUID = 12052301L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRouteUser routeUser = new QRouteUser("routeUser");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath email = createString("password");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final StringPath id = createString("id");

    public final QName name;

    public final BooleanPath smoker = createBoolean("smoker");

    public final QVehicle vehicle;

    public QRouteUser(String variable) {
        this(RouteUser.class, forVariable(variable), INITS);
    }

    public QRouteUser(Path<? extends RouteUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRouteUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRouteUser(PathMetadata metadata, PathInits inits) {
        this(RouteUser.class, metadata, inits);
    }

    public QRouteUser(Class<? extends RouteUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.name = inits.isInitialized("name") ? new QName(forProperty("name")) : null;
        this.vehicle = inits.isInitialized("vehicle") ? new QVehicle(forProperty("vehicle")) : null;
    }

}

