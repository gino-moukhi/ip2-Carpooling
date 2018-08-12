package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.dto.RouteDto;
import be.kdg.ip2.carpooling.service.communication.CommunicationService;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/communication")
@Slf4j
public class CommunicationController {
    private CommunicationService communicationService;
    private RouteService routeService;

    @Autowired
    public CommunicationController(CommunicationService communicationService, RouteService routeService) {
        this.communicationService = communicationService;
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<CommunicationRequestDto> getAll() {
        return communicationService.findAllAsDto();
    }

    @GetMapping("/route")
    public List<CommunicationRequestDto> getAllFromRoute(@RequestParam String routeId) {
        return communicationService.findCommunicationRequestsByRouteIdAsDto(routeId);
    }

    @GetMapping("/route/status")
    public List<CommunicationRequestDto> getAllFromRouteAndStatus(@RequestParam String routeId, @RequestParam String status) {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        return communicationService.findCommunicationRequestsByRouteIdAndRequestStatusAsDto(routeId, requestStatus);
    }

    @GetMapping("/user")
    public List<CommunicationRequestDto> getAllFromUser(@RequestParam String userId) {
        return communicationService.findCommunicationRequestsByUserIdAsDto(userId);
    }

    @GetMapping("/user/status")
    public List<CommunicationRequestDto> getAllFromUserAndStatus(@RequestParam String userId, @RequestParam String status) {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        return communicationService.findCommunicationRequestsByUserIdAndRequestStatusAsDto(userId, requestStatus);
    }

    @GetMapping("/route/user")
    public List<CommunicationRequestDto> getAllFromRouteAndUser(@RequestParam String routeId, @RequestParam String userId) {
        return communicationService.findCommunicationRequestByRouteIdAndUserIdAsDto(routeId, userId);
    }

    @GetMapping("/route/user/status")
    public CommunicationRequestDto getAllFromRouteAndUserAndStatus(@RequestParam String routeId, @RequestParam String userId, @RequestParam String status) {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        return communicationService.findCommunicationRequestsByRouteIdAndUserIdAndRequestStatusAsDto(routeId, userId, requestStatus);
    }

    @GetMapping("/status")
    public List<CommunicationRequestDto> getAllFromStatus(@RequestParam String status) {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        return communicationService.findCommunicationRequestsByRequestStatusAsDto(requestStatus);
    }

    @GetMapping
    public CommunicationRequestDto getById(@RequestParam String id) {
        return communicationService.findCommunicationRequestDtoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteDto createCommunicationRequest(@RequestBody CommunicationRequestDto requestDto) throws RouteServiceException {
        CommunicationRequest communicationRequest = communicationService.addCommunicationRequest(requestDto);
        if (communicationRequest != null) {
            return routeService.addCommunicationRequestToRoute(communicationRequest);
        } else {
            return new RouteDto(routeService.findRouteById(requestDto.getRouteId()));
        }
    }

    @PutMapping
    public RouteDto updateCommunicationRequest(@RequestBody CommunicationRequestDto requestDto) throws RouteServiceException {
        CommunicationRequest communicationRequest = communicationService.updateCommunicationRequest(requestDto);
        if (communicationRequest != null) {
            return routeService.updateCommunicationRequestOfRoute(communicationRequest);
        } else {
            return new RouteDto(routeService.findRouteById(requestDto.getRouteId()));
        }
    }

    @PutMapping("/status")
    public RouteDto updateCommunicationRequestStatus(@RequestParam String requestId, @RequestParam String status) throws RouteServiceException {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        CommunicationRequest request = communicationService.updateCommunicationRequestStatus(requestId, requestStatus);
        if (request != null) {
            return routeService.updateCommunicationRequestOfRoute(request);
        } else {
            return new RouteDto(routeService.findRouteById(communicationService.findCommunicationRequestById(requestId).getRouteId()));
        }
    }

    private CommunicationRequestStatus statusConverter(int status) {
        switch (status) {
            case 0:
                return CommunicationRequestStatus.IN_PROGRESS;
            case 1:
                return CommunicationRequestStatus.ACCEPTED;
            default:
                return CommunicationRequestStatus.DECLINED;
        }
    }
}
