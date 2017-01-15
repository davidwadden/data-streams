package io.dwadden.gps.fakes;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.springframework.test.web.client.ExpectedCount.between;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "http-gateway.initial-delay=100",
        "http-gateway.fixed-delay=100",
        "http-gateway.endpoint=http://some.api/endpoint",
    }
)
class GpsWaypointHttpGatewayIntegrationTest {

    final GpsWaypointHttpGatewayProperties properties;
    final RestOperations restOperations;
    MockRestServiceServer mockServer;

    @SuppressWarnings("unused")
    String postBody;

    @Autowired
    GpsWaypointHttpGatewayIntegrationTest(GpsWaypointHttpGatewayProperties properties,
                                          RestOperations restOperations) {
        this.properties = properties;
        this.restOperations = restOperations;
    }

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
    }

    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    @DisplayName("should wire up and make HTTP calls via Scheduler")
    @Test
    void sendRequests() {
        properties.setEndpoint("http://some.api/endpoint");

        mockServer
            .expect(between(3, 5), requestTo("http://some.api/endpoint"))
            .andExpect(request -> postBody = request.getBody().toString())
            .andRespond(withStatus(HttpStatus.ACCEPTED));

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> mockServer.verify(), 400, TimeUnit.MILLISECONDS).get();

        assertThatJson(postBody).hasSameStructureAs(makeGpsWaypoint());
    }

    private static RawGpsWaypoint makeGpsWaypoint() {
        return RawGpsWaypoint.builder().build();
    }
}
