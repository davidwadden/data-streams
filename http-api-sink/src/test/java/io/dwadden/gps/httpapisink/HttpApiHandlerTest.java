package io.dwadden.gps.httpapisink;

import io.dwadden.gps.entities.AvroGpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import reactor.test.publisher.TestPublisher;

import java.time.Instant;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
class HttpApiHandlerTest {

    static final String API_ENDPOINT = "http://some.api/endpoint";

    HttpApiSinkProperties properties;
    MockRestServiceServer mockServer;
    TestPublisher<Message<AvroGpsWaypoint>> testPublisher;
    HttpApiHandler handler;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        testPublisher = TestPublisher.create();

        properties = new HttpApiSinkProperties();
        properties.setBatchSize(3);
        properties.setUploadEndpoint(API_ENDPOINT);

        handler = new HttpApiHandler(restTemplate, properties, testPublisher);
    }

    @DisplayName("should handle widget and make HTTP API call")
    @Test
    void handleMessage() {
        mockServer
            .expect(requestTo(API_ENDPOINT))
            .andExpect(content().string("2001"))
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        testPublisher.next(makeAvroMessage());

        mockServer.verify();
    }

    private static Message<AvroGpsWaypoint> makeAvroMessage() {
        AvroGpsWaypoint avroWidget = AvroGpsWaypoint.newBuilder()
            .setId(2001L)
            .setLatitude(41.921855d)
            .setLongitude(-87.633487d)
            .setHeading(290)
            .setSpeed(72)
            .setTimestamp(1484466954L)
            .build();

        return MessageBuilder
            .withPayload(avroWidget)
            .build();
    }

}
