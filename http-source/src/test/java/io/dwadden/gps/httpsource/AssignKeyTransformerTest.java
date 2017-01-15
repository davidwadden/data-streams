package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.GpsWaypoint;
import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AssignKeyTransformerTest {

    @Mock IdGenerator<Long> idGenerator;
    @InjectMocks AssignKeyTransformer transformer;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @DisplayName("should assign a unique key to the GPS waypoint")
    @Test
    void assignKey() {
        when(idGenerator.generateId()).thenReturn(2001L);
        RawGpsWaypoint rawGpsWaypoint = makeRawGpsWaypoint();

        GpsWaypoint gpsWaypoint = transformer.assignKey(rawGpsWaypoint);

        assertThat(gpsWaypoint.getId()).isEqualTo(2001L);
        assertThat(gpsWaypoint).isEqualToIgnoringGivenFields(rawGpsWaypoint, "id");

        verify(idGenerator).generateId();
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

}