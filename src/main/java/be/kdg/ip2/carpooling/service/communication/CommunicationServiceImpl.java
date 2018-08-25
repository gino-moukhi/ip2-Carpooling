package be.kdg.ip2.carpooling.service.communication;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.repository.communication.CommunicationRepository;
import be.kdg.ip2.carpooling.util.DecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    public enum MethodName {
        FIND_ALL, FIND_BY_ROUTEID, FIND_BY_USERID, FIND_BY_ROUTEID_USERID, FIND_BY_STATUS, FIND_BY_ROUTEID_STATUS,
        FIND_BY_USERID_STATUS
    }

    private CommunicationRepository communicationRepository;

    @Autowired
    public CommunicationServiceImpl(CommunicationRepository communicationRepository) {
        this.communicationRepository = communicationRepository;
    }

    @Override
    public List<CommunicationRequest> findAll() {
        return communicationRepository.findAll();
    }

    @Override
    public List<CommunicationRequestDto> findAllAsDto() {
        return findAllDto(MethodName.FIND_ALL, null, null, null);
    }

    @Override
    public CommunicationRequest findCommunicationRequestById(String id) {
        return communicationRepository.findCommunicationRequestById(id);
    }

    @Override
    public CommunicationRequestDto findCommunicationRequestDtoById(String id) {
        return new CommunicationRequestDto(communicationRepository.findCommunicationRequestById(id));
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestsByRouteId(String routeId) {
        return communicationRepository.findCommunicationRequestsByRouteId(routeId);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestsByRouteIdAsDto(String routeId) {
        return findAllDto(MethodName.FIND_BY_ROUTEID, routeId, null, null);
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestsByUserId(String userId) {
        return communicationRepository.findCommunicationRequestsByUser_Id(userId);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestsByUserIdAsDto(String userId) {
        return findAllDto(MethodName.FIND_BY_USERID, null, userId, null);
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestByRouteIdAndUserId(String routeId, String userId) {
        return communicationRepository.findCommunicationRequestsByRouteIdAndUser_Id(routeId, userId);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestByRouteIdAndUserIdAsDto(String routeId, String userId) {
        return findAllDto(MethodName.FIND_BY_ROUTEID_USERID, routeId, userId, null);
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestsByRequestStatus(CommunicationRequestStatus status) {
        return communicationRepository.findCommunicationRequestsByRequestStatus(status);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestsByRequestStatusAsDto(CommunicationRequestStatus status) {
        return findAllDto(MethodName.FIND_BY_STATUS, null, null, status);
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestsByRouteIdAndRequestStatus(String routeId, CommunicationRequestStatus status) {
        return communicationRepository.findCommunicationRequestsByRouteIdAndRequestStatus(routeId, status);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestsByRouteIdAndRequestStatusAsDto(String routeId, CommunicationRequestStatus status) {
        return findAllDto(MethodName.FIND_BY_ROUTEID_STATUS, routeId, null, status);
    }

    @Override
    public List<CommunicationRequest> findCommunicationRequestsByUserIdAndRequestStatus(String userId, CommunicationRequestStatus status) {
        return communicationRepository.findCommunicationRequestsByUser_IdAndRequestStatus(userId, status);
    }

    @Override
    public List<CommunicationRequestDto> findCommunicationRequestsByUserIdAndRequestStatusAsDto(String userId, CommunicationRequestStatus status) {
        return findAllDto(MethodName.FIND_BY_USERID_STATUS, null, userId, status);
    }

    @Override
    public CommunicationRequest findCommunicationRequestsByRouteIdAndUserIdAndRequestStatus(String routId, String userId, CommunicationRequestStatus status) {
        return communicationRepository.findCommunicationRequestsByRouteIdAndUser_IdAndRequestStatus(routId, userId, status);
    }

    @Override
    public CommunicationRequestDto findCommunicationRequestsByRouteIdAndUserIdAndRequestStatusAsDto(String routId, String userId, CommunicationRequestStatus status) {
        return new CommunicationRequestDto(communicationRepository.findCommunicationRequestsByRouteIdAndUser_IdAndRequestStatus(routId, userId, status));
    }

    @Override
    public CommunicationRequest addCommunicationRequest(CommunicationRequest request) {
        request.getOrigin().setLocation(DecimalUtil.roundPoint(request.getOrigin().getLocation()));
        request.getDestination().setLocation(DecimalUtil.roundPoint(request.getDestination().getLocation()));
        return saveWithCheck(request);
    }

    @Override
    public CommunicationRequest addCommunicationRequest(CommunicationRequestDto request) {
        CommunicationRequest communicationRequest = new CommunicationRequest(request);
        communicationRequest.getOrigin().setLocation(DecimalUtil.roundPoint(communicationRequest.getOrigin().getLocation()));
        communicationRequest.getDestination().setLocation(DecimalUtil.roundPoint(communicationRequest.getDestination().getLocation()));
        return saveWithCheck(communicationRequest);
    }

    @Override
    public CommunicationRequest updateCommunicationRequest(CommunicationRequest request) {
        return checkIfUpdated(request);
    }

    @Override
    public CommunicationRequest updateCommunicationRequest(CommunicationRequestDto request) {
        CommunicationRequest communicationRequest = new CommunicationRequest(request);
        return checkIfUpdated(communicationRequest);
    }

    @Override
    public CommunicationRequest updateCommunicationRequestStatus(String id, CommunicationRequestStatus status) {
        CommunicationRequest found = communicationRepository.findCommunicationRequestById(id);
        if (found.getRequestStatus().equals(status)) {
            return null;
        } else {
            found.setRequestStatus(status);
            return communicationRepository.save(found);
        }
    }

    @Override
    public void deleteCommunicationRequestById(String id) {
        communicationRepository.deleteById(id);
    }

    @Override
    public void deleteCommunicationRequestsByRouteId(String routeId) {
        List<CommunicationRequest> requestsToDelete = communicationRepository.findCommunicationRequestsByRouteId(routeId);
        requestsToDelete.forEach(request -> communicationRepository.deleteById(request.getId()));
    }

    @Override
    public void deleteAll() {
        communicationRepository.deleteAll();
    }

    private CommunicationRequest saveWithCheck(CommunicationRequest request) {
        CommunicationRequest foundRequest = findCommunicationRequestsByRouteIdAndUserIdAndRequestStatus(request.getRouteId(), request.getUser().getId(), request.getRequestStatus());
        if (foundRequest != null) {
            request.setId(foundRequest.getId());
        }
        return communicationRepository.save(request);
    }

    private List<CommunicationRequestDto> findAllDto(MethodName methodName, @Nullable String routeId, @Nullable String userId, @Nullable CommunicationRequestStatus status) {
        List<CommunicationRequest> all = new ArrayList<>();
        switch (methodName) {
            case FIND_ALL:
                all = communicationRepository.findAll();
                break;
            case FIND_BY_ROUTEID:
                all = communicationRepository.findCommunicationRequestsByRouteId(routeId);
                break;
            case FIND_BY_USERID:
                all = communicationRepository.findCommunicationRequestsByUser_Id(userId);
                break;
            case FIND_BY_ROUTEID_USERID:
                all = communicationRepository.findCommunicationRequestsByRouteIdAndUser_Id(routeId, userId);
                break;
            case FIND_BY_STATUS:
                all = communicationRepository.findCommunicationRequestsByRequestStatus(status);
                break;
            case FIND_BY_ROUTEID_STATUS:
                all = communicationRepository.findCommunicationRequestsByRouteIdAndRequestStatus(routeId, status);
                break;
            case FIND_BY_USERID_STATUS:
                all = communicationRepository.findCommunicationRequestsByUser_IdAndRequestStatus(userId, status);
                break;
        }
        List<CommunicationRequestDto> allDto = new ArrayList<>();
        all.forEach(request -> allDto.add(new CommunicationRequestDto(request)));
        return allDto;
    }

    private CommunicationRequest checkIfUpdated(CommunicationRequest request) {
        if (communicationRepository.findCommunicationRequestById(request.getId()).equals(request)) {
            return null;
        } else {
            request.getOrigin().setLocation(DecimalUtil.roundPoint(request.getOrigin().getLocation()));
            request.getDestination().setLocation(DecimalUtil.roundPoint(request.getDestination().getLocation()));
            return communicationRepository.save(request);
        }
    }
}