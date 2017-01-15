package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.GpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GpsWaypointGeneratorTest {

    GpsWaypointGenerator transformer;

    @BeforeEach
    void setUp() {
        transformer = new GpsWaypointGenerator();
    }

    @DisplayName("should transform from a key to a widget")
    @Test
    void transform() {
        GpsWaypoint gpsWaypoint = transformer.transform(47L);

        assertThat(gpsWaypoint.getKey()).isEqualTo(47L);
        assertThat(gpsWaypoint.getType()).isEqualTo("some-type");
        assertThat(gpsWaypoint.getPayload()).isEqualTo("some-payload");
    }

}
