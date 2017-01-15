package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.GpsWaypoint;
import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class AssignKeyTransformer {

    IdGenerator<Long> idGenerator;

    public AssignKeyTransformer(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public GpsWaypoint assignKey(RawGpsWaypoint rawGpsWaypoint) {
        return GpsWaypoint.builder()
            .id(idGenerator.generateId())
            .latitude(rawGpsWaypoint.getLatitude())
            .longitude(rawGpsWaypoint.getLongitude())
            .heading(rawGpsWaypoint.getHeading())
            .speed(rawGpsWaypoint.getSpeed())
            .timestamp(rawGpsWaypoint.getTimestamp())
            .build();
    }

}
