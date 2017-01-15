package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.GpsWaypoint;
import org.springframework.stereotype.Component;

@Component
public class GpsWaypointGenerator {

    public GpsWaypoint transform(Long key) {

        return GpsWaypoint.builder()
            .key(key)
            .type("some-type")
            .payload("some-payload")
            .build();
    }
}
