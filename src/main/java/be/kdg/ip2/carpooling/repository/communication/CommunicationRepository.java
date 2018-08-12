package be.kdg.ip2.carpooling.repository.communication;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends MongoRepository<CommunicationRequest, String>, QuerydslPredicateExecutor<CommunicationRequest> {
    CommunicationRequest findCommunicationRequestById(String id);

    List<CommunicationRequest> findCommunicationRequestsByRouteId(String routeId);

    List<CommunicationRequest> findCommunicationRequestsByUser_Id(String userId);

    List<CommunicationRequest> findCommunicationRequestsByRouteIdAndUser_Id(String routeId, String userId);

    List<CommunicationRequest> findCommunicationRequestsByRequestStatus(CommunicationRequestStatus status);

    List<CommunicationRequest> findCommunicationRequestsByRouteIdAndRequestStatus(String routeId, CommunicationRequestStatus status);

    List<CommunicationRequest> findCommunicationRequestsByUser_IdAndRequestStatus(String userId, CommunicationRequestStatus status);

    CommunicationRequest findCommunicationRequestsByRouteIdAndUser_IdAndRequestStatus(String routeId, String userId, CommunicationRequestStatus status);
}
