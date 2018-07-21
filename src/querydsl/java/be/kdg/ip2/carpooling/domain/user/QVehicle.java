package be.kdg.ip2.carpooling.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVehicle is a Querydsl query type for Vehicle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QVehicle extends BeanPath<Vehicle> {

    private static final long serialVersionUID = 368722693L;

    public static final QVehicle vehicle = new QVehicle("vehicle");

    public final StringPath brand = createString("brand");

    public final NumberPath<Double> fuelConsumption = createNumber("fuelConsumption", Double.class);

    public final NumberPath<Integer> numberOfPassengers = createNumber("numberOfPassengers", Integer.class);

    public final EnumPath<VehicleType> type = createEnum("type", VehicleType.class);

    public QVehicle(String variable) {
        super(Vehicle.class, forVariable(variable));
    }

    public QVehicle(Path<? extends Vehicle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVehicle(PathMetadata metadata) {
        super(Vehicle.class, metadata);
    }

}

