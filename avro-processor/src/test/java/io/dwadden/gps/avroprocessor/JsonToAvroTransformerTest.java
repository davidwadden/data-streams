package io.dwadden.gps.avroprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@FieldDefaults(level = AccessLevel.PRIVATE)
class JsonToAvroTransformerTest {

    ObjectMapper objectMapper;
    JsonToAvroTransformer transformer;

    @BeforeEach
    void setUp() {
        objectMapper = makeObjectMapper();
        transformer = new JsonToAvroTransformer(objectMapper);
    }

    @DisplayName("should transform to Avro object")
    @Test
    void transform() {
        GpsWaypoint gpsWaypoint = makeGpsWaypoint();
        String waypointJson = makeWaypointJson();

        AvroGpsWaypoint avroWaypoint = transformer.transform(waypointJson);

        assertAll(
            () -> assertThat(avroWaypoint).isEqualToIgnoringGivenFields(gpsWaypoint, "timestamp"),
            () -> assertThat(avroWaypoint.getTimestamp()).isEqualTo(1484466954L)
        );
    }

    @SneakyThrows(JsonProcessingException.class)
    private String makeWaypointJson() {
        return objectMapper
            .writer()
            .withDefaultPrettyPrinter()
            .writeValueAsString(makeGpsWaypoint());
    }


    private static GpsWaypoint makeGpsWaypoint() {
        return GpsWaypoint.builder()
            .id(2001L)
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
