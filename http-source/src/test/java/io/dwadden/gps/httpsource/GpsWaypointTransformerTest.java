package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
class GpsWaypointTransformerTest {

    GpsWaypointTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new GpsWaypointTransformer();
    }

    @DisplayName("should transform a GpsWaypoint into an AvroGpsWaypoint")
    @Test
    void transform() {
        GpsWaypoint gpsWaypoint = GpsWaypoint.builder()
            .key(1004L)
            .type("some-type")
            .payload("some-payload")
            .build();

        AvroGpsWaypoint avroWidget = transformer.transform(gpsWaypoint);

        assertThat(avroWidget.getKey()).isEqualTo(1004L);
        assertThat(avroWidget.getType()).isEqualTo("some-type");
        assertThat(avroWidget.getPayload()).isEqualTo("some-payload");
    }

}
