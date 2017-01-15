package io.dwadden.gps.httpsource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpSourceIntegrationTest {

    static final ObjectMapper objectMapper = makeObjectMapper();

    final TestRestTemplate testRestTemplate;
    final Source source;
    final MessageCollector collector;

    @Autowired
    HttpSourceIntegrationTest(TestRestTemplate testRestTemplate,
                              Source source,
                              MessageCollector collector) {
        this.testRestTemplate = testRestTemplate;
        this.source = source;
        this.collector = collector;
    }

    @SneakyThrows({IOException.class, InterruptedException.class})
    @DisplayName("should integration test the source")
    @Test
    void ingest() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("/ingest"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(makeWaypointJson());

        ResponseEntity<Void> responseEntity =
            testRestTemplate.postForEntity("/ingest", requestEntity, null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Message<?> msg = collector.forChannel(source.output()).poll(1, TimeUnit.SECONDS);

        assertThat(msg).isNotNull();
        assertThat(msg.getPayload()).isExactlyInstanceOf(String.class);

        String payload = (String) msg.getPayload();
        GpsWaypoint waypoint = objectMapper.readValue(payload, GpsWaypoint.class);
        assertThat(waypoint.getId()).isEqualTo(1L);
    }

    @SneakyThrows(JsonProcessingException.class)
    private static String makeWaypointJson() {
        return objectMapper
            .writer()
            .withDefaultPrettyPrinter()
            .writeValueAsString(makeRawGpsWaypoint());
    }

    private static RawGpsWaypoint makeRawGpsWaypoint() {
        return RawGpsWaypoint.builder()
            .latitude(41.921855d)
            .longitude(-87.633487d)
            .heading(290)
            .speed(72)
            .timestamp(Instant.ofEpochSecond(1484466954L))
            .build();
    }

    private static ObjectMapper makeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

}
