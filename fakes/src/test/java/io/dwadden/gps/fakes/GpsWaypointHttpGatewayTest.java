package io.dwadden.gps.fakes;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
class GpsWaypointHttpGatewayTest {

    GpsWaypointHttpGatewayProperties properties;
    MockRestServiceServer mockServer;
    GpsWaypointHttpGateway gateway;

    @SuppressWarnings("unused")
    String postBody;

    @BeforeEach
    void setUp() {
        properties = new GpsWaypointHttpGatewayProperties();
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        gateway = new GpsWaypointHttpGateway(properties, restTemplate);
    }

    @DisplayName("")
    @Test
    void sendRequest() {
        properties.setEndpoint("http://some.api/endpoint");

        mockServer.expect(requestTo("http://some.api/endpoint"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(r -> postBody = r.getBody().toString())
            .andRespond(withStatus(HttpStatus.ACCEPTED));

        gateway.sendRequest();

        mockServer.verify();

        assertThat(postBody).isEqualTo("some-request");
    }

}
