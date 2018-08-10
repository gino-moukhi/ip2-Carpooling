package be.kdg.ip2.carpooling.integration;

import be.kdg.ip2.carpooling.domain.communication.CommunicationRequest;
import be.kdg.ip2.carpooling.domain.communication.CommunicationRequestStatus;
import be.kdg.ip2.carpooling.domain.route.Route;
import be.kdg.ip2.carpooling.domain.user.Gender;
import be.kdg.ip2.carpooling.domain.user.Name;
import be.kdg.ip2.carpooling.domain.user.Vehicle;
import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.dto.RouteLocationDto;
import be.kdg.ip2.carpooling.dto.RouteUserDto;
import be.kdg.ip2.carpooling.service.communication.CommunicationService;
import be.kdg.ip2.carpooling.service.route.RouteService;
import be.kdg.ip2.carpooling.service.route.RouteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommunicationServiceTest {
    @Autowired
    private CommunicationService communicationService;
    @Autowired
    private RouteService routeService;

    @Before
    public void setUp() {
        communicationService.deleteAll();
        String routeId = "5b675815d3303d3a74c4a339";
        RouteLocationDto origin = new RouteLocationDto("Paalstraat, 2900 Schoten, België", 51.257071, 4.504916);
        RouteLocationDto destination = new RouteLocationDto("Tweegezusterslaan 82, 2100 Antwerpen, België", 51.227694, 4.476514);
        String sophieId = "5b534318d3303d2c7090d940";
        String comment = "I work at \"Frietworld\", if you drop me off, I'll buy you some fries";
        CommunicationRequestDto request = new CommunicationRequestDto(routeId, sophieId, origin, destination, comment, CommunicationRequestStatus.IN_PROGRESS);
        communicationService.addCommunicationRequest(request);
    }

    @Test
    public void testAddCommunicationRequest() {
        List<CommunicationRequest> all = communicationService.findAll();
        assertThat(all.size(), equalTo(1));

        String routeId = "5b675815d3303d3a74c4a339";
        RouteLocationDto origin = new RouteLocationDto("Eikenlei 10, 2960 Sint-Job-in-'t-Goor, België", 51.303693, 4.566765);
        RouteLocationDto destination = new RouteLocationDto("Parking Het Ven, 2900 Schoten, België", 51.251999, 4.502031);
        String ginoId = "5b534318d3303d2c7090d93e";
        String comment = "I just need to be somewhere close to that parking location";
        CommunicationRequestDto request = new CommunicationRequestDto(routeId, ginoId, origin, destination, comment, CommunicationRequestStatus.IN_PROGRESS);
        communicationService.addCommunicationRequest(request);
        all = communicationService.findAll();
        assertThat(all.size(), equalTo(2));
    }

    @Test
    public void removePassengerFromRouteTest() throws RouteServiceException {
        /*String routeId = "5b675815d3303d3a74c4a339";
        String ginoId = "5b534318d3303d2c7090d93e";
        RouteUserDto userDto = new RouteUserDto(ginoId, "restemail@mail.ru", new Name("Gino", "Moukhi"), 21, Gender.MALE, false, new Vehicle());
        RouteLocationDto origin = new RouteLocationDto("Eikenlei 10, 2960 Sint-Job-in-'t-Goor, België", 51.303693, 4.566765);
        RouteLocationDto destination = new RouteLocationDto("Parking Het Ven, 2900 Schoten, België", 51.251999, 4.502031);
        String comment = "I just need to be somewhere close to that parking location";
        CommunicationRequestDto request = new CommunicationRequestDto(routeId, ginoId, origin, destination, comment, CommunicationRequestStatus.IN_PROGRESS);
        Route routeById;
        routeById = routeService.findRouteById(routeId);
        assertThat(routeById.getAvailablePassengers(), equalTo(4));
        assertThat(routeById.getPassengers().size(), equalTo(0));
        assertThat(routeById.getCommunicationRequests().size(), equalTo(1));
        communicationService.addCommunicationRequest(request);

        CommunicationRequest addedRequest = communicationService.findCommunicationRequestsByRouteIdAndUserIdAndRequestStatus(request.getRouteId(), request.getUserId(), request.getRequestStatus());

        routeById = routeService.findRouteById(routeId);
        assertThat(routeById.getAvailablePassengers(), equalTo(4));
        assertThat(routeById.getPassengers().size(), equalTo(0));
        assertThat(routeById.getCommunicationRequests().size(), equalTo(2));

        communicationService.updateCommunicationRequestStatus(addedRequest.getId(), CommunicationRequestStatus.ACCEPTED);
        routeById = routeService.findRouteById(routeId);
        assertThat(routeById.getAvailablePassengers(), equalTo(3));
        assertThat(routeById.getPassengers().size(), equalTo(1));
        assertThat(routeById.getPassengers().get(0).getName().getFirstName(), equalTo("Gino"));
        assertThat(routeById.getPassengers().get(0).getName().getLastName(), equalTo("Moukhi"));
        assertThat(routeById.getCommunicationRequests().size(), equalTo(2));

        communicationService.updateCommunicationRequestStatus(addedRequest.getId(), CommunicationRequestStatus.DECLINED);
        routeById = routeService.findRouteById(routeId);
        assertThat(routeById.getAvailablePassengers(), equalTo(4));
        assertThat(routeById.getPassengers().size(), equalTo(0));
        assertThat(routeById.getCommunicationRequests().size(), equalTo(2));*/
    }

    @After
    public void tearDown() {
        communicationService.deleteAll();
    }
}
/*@Test
    public void testCommunication() {
        try {
            Route routeById = communicationService.findRouteById("5b675815d3303d3a74c4a339");
            if (!routeById.getCommunicationRequests().isEmpty()) {
                routeById.setCommunicationRequests(new ArrayList<>());
                communicationService.addRoute(routeById);
            }
        } catch (RouteServiceException e) {
            e.printStackTrace();
        }
        RouteLocationDto origin = new RouteLocationDto("Paalstraat, 2900 Schoten, België", 51.257071, 4.504916);
        RouteLocationDto destination = new RouteLocationDto("Tweegezusterslaan 82, 2100 Antwerpen, België", 51.227694, 4.476514);
        String ginoId = "5b534318d3303d2c7090d93e";
        CommunicationRequestDto requestDto = new CommunicationRequestDto("5b675815d3303d3a74c4a339", ginoId, origin, destination,
                "Ik werk in de frituur \"Frietworld\", als u mij wilt afzetten dan krijgt u een pak fiet van mij",CommunicationRequestStatus.IN_PROGRESS);
        //communicationService.addCommunicationRequestToRoute(requestDto);
    }*/