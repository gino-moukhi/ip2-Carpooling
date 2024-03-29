package be.kdg.ip2.carpooling.service.communication;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;

import java.util.List;

public interface CommunicationService {
    List<CommunicationRequest> findAll();

    List<CommunicationRequestDto> findAllAsDto();

    CommunicationRequest findCommunicationRequestById(String id);

    CommunicationRequestDto findCommunicationRequestDtoById(String id);

    List<CommunicationRequest> findCommunicationRequestsByRouteId(String routeId);

    List<CommunicationRequestDto> findCommunicationRequestsByRouteIdAsDto(String routeId);

    List<CommunicationRequest> findCommunicationRequestsByUserId(String userId);

    List<CommunicationRequestDto> findCommunicationRequestsByUserIdAsDto(String userId);

    List<CommunicationRequest> findCommunicationRequestByRouteIdAndUserId(String routeId, String userId);

    List<CommunicationRequestDto> findCommunicationRequestByRouteIdAndUserIdAsDto(String routeId, String userId);

    List<CommunicationRequest> findCommunicationRequestsByRequestStatus(CommunicationRequestStatus status);

    List<CommunicationRequestDto> findCommunicationRequestsByRequestStatusAsDto(CommunicationRequestStatus status);

    List<CommunicationRequest> findCommunicationRequestsByRouteIdAndRequestStatus(String routeId, CommunicationRequestStatus status);

    List<CommunicationRequestDto> findCommunicationRequestsByRouteIdAndRequestStatusAsDto(String routeId, CommunicationRequestStatus status);

    List<CommunicationRequest> findCommunicationRequestsByUserIdAndRequestStatus(String userId, CommunicationRequestStatus status);

    List<CommunicationRequestDto> findCommunicationRequestsByUserIdAndRequestStatusAsDto(String userId, CommunicationRequestStatus status);

    CommunicationRequest findCommunicationRequestsByRouteIdAndUserIdAndRequestStatus(String routId, String userId, CommunicationRequestStatus status);

    CommunicationRequestDto findCommunicationRequestsByRouteIdAndUserIdAndRequestStatusAsDto(String routId, String userId, CommunicationRequestStatus status);

    CommunicationRequest addCommunicationRequest(CommunicationRequest request);

    CommunicationRequest addCommunicationRequest(CommunicationRequestDto request);

    CommunicationRequest updateCommunicationRequest(CommunicationRequest request);

    CommunicationRequest updateCommunicationRequest(CommunicationRequestDto request);

    CommunicationRequest updateCommunicationRequestStatus(String id, CommunicationRequestStatus status);

    void deleteCommunicationRequestById(String id);

    void deleteCommunicationRequestsByRouteId(String routeId);

    void deleteAll();
}
