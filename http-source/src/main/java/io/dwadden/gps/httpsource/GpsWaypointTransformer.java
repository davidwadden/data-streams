package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class GpsWaypointTransformer {

    static Mapper dozerBeanMapper = new DozerBeanMapper();

    public AvroGpsWaypoint transform(GpsWaypoint gpsWaypoint) {

        return dozerBeanMapper.map(gpsWaypoint, AvroGpsWaypoint.class);
    }

}
