package be.kdg.ip2.carpooling.integration;

import be.kdg.ip2.carpooling.dto.CommunicationRequestDto;
import be.kdg.ip2.carpooling.service.communication.CommunicationService;
import be.kdg.ip2.carpooling.service.route.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CommunicationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private CommunicationService communicationService;
    @MockBean
    private RouteService routeService;

    @Test
    public void addCommunicationRequestTest() {
        CommunicationRequestDto incoming = new CommunicationRequestDto();
        //when
        ResponseEntity<CommunicationRequestDto> response = restTemplate.postForEntity("/communication", incoming, CommunicationRequestDto.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
