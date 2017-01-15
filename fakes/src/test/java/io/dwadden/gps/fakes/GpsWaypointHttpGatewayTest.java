package io.dwadden.gps.fakes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertAll;
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

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.getMessageConverters().add(0, converter);

        gateway = new GpsWaypointHttpGateway(properties, restTemplate);
    }

    @DisplayName("should make HTTP calls to ingestor endpoint")
    @Test
    void sendRequest() {
        properties.setEndpoint("http://some.api/endpoint");

        mockServer.expect(requestTo("http://some.api/endpoint"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(r -> postBody = r.getBody().toString())
            .andRespond(withStatus(HttpStatus.ACCEPTED));

        gateway.sendRequest();

        mockServer.verify();

        assertAll(
            () -> assertThatJson(postBody).node("latitude").isEqualTo(41.921855),
            () -> assertThatJson(postBody).node("longitude").isEqualTo(-87.633487),
            () -> assertThatJson(postBody).node("heading").isEqualTo(290),
            () -> assertThatJson(postBody).node("speed").isEqualTo(72),
            () -> assertThatJson(postBody).node("timestamp").isEqualTo("2017-01-15T07:55:54Z")
        );
    }

}
