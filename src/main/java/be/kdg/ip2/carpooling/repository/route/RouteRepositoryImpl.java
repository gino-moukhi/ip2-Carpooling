package be.kdg.ip2.carpooling.repository.route;

import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.route.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class RouteRepositoryImpl implements CustomRouteRepository {
    private final MongoOperations mongoOperations;

    @Autowired
    public RouteRepositoryImpl(MongoOperations mongoOperations) {
        Assert.notNull(mongoOperations, "MongoOperations must not be null!");
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Route> findExistingRoutesFromPlaces(SourceType sourceType1, SourceType sourceType2, Point origin, Point destination) {
        Query query = new Query();
        if (sourceType1.equals(SourceType.WAYPOINT) && sourceType2.equals(SourceType.WAYPOINT)) {
            query.addCriteria(Criteria.where("definition.waypoints").elemMatch(Criteria.where("location").is(new GeoJsonPoint(origin)))
                    .elemMatch(Criteria.where("location").is(new GeoJsonPoint(destination))));
        } else {
            CriteriaGeneratorFromSourceType(sourceType1, origin, query);
            CriteriaGeneratorFromSourceType(sourceType2, destination, query);
        }
        return mongoOperations.find(query, Route.class);
    }

    private void CriteriaGeneratorFromSourceType(SourceType sourceType, Point point, Query query) {
        switch (sourceType) {
            case ORIGIN:
                query.addCriteria(Criteria.where("definition.origin.location").is(new GeoJsonPoint(point)));
                break;
            case DESTINATION:
                query.addCriteria(Criteria.where("definition.destination.location").is(new GeoJsonPoint(point)));
                break;
            case WAYPOINT:
                query.addCriteria(Criteria.where("definition.waypoints").elemMatch(Criteria.where("location").is(new GeoJsonPoint(point))));
                break;
        }
    }
}
