package be.kdg.ip2.carpooling.controller;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.service.communication.CommunicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/communication")
@Slf4j
public class CommunicationController {
    private CommunicationService communicationService;

    @Autowired
    public CommunicationController(CommunicationService communicationService) {
        this.communicationService = communicationService;
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
    public void createCommunicationRequest(@RequestBody CommunicationRequestDto requestDto) {
        communicationService.addCommunicationRequest(requestDto);
    }

    @PutMapping
    public void updateCommunicationRequest(@RequestBody CommunicationRequestDto requestDto) {
        communicationService.updateCommunicationRequest(requestDto);
    }

    @PutMapping("/status")
    public void updateCommunicationRequestStatus(@RequestParam String requestId, @RequestParam String status) {
        CommunicationRequestStatus requestStatus = statusConverter(Integer.parseInt(status));
        communicationService.updateCommunicationRequestStatus(requestId, requestStatus);
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
