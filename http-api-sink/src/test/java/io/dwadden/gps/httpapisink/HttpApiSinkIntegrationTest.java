package io.dwadden.gps.httpapisink;

import io.dwadden.gps.entities.AvroGpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "httpApiSink.batchSize=3",
        "httpApiSink.uploadEndpoint=http://some.api/endpoint",
    }
)
class HttpApiSinkIntegrationTest {

    final Sink sink;
    final RestOperations restOperations;
    MockRestServiceServer mockServer;

    @Autowired
    HttpApiSinkIntegrationTest(Sink sink, RestOperations restOperations) {
        this.sink = sink;
        this.restOperations = restOperations;
    }

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
    }

    @DisplayName("should test the Batch Sink integrates properly")
    @Test
    void batchSink() {
        mockServer
            .expect(requestTo("http://some.api/endpoint"))
            .andExpect(content().string("2001"))
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        sink.input().send(makeMessage());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> mockServer.verify(), 1, TimeUnit.SECONDS);
    }

    private static Message<AvroGpsWaypoint> makeMessage() {
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
