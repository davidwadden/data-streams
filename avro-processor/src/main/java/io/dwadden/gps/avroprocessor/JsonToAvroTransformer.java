package io.dwadden.gps.avroprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class JsonToAvroTransformer {

    ObjectMapper objectMapper;

    @Autowired
    public JsonToAvroTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows(IOException.class)
    public AvroGpsWaypoint transform(String waypointJson) {

        GpsWaypoint gpsWaypoint = objectMapper.readValue(waypointJson, GpsWaypoint.class);

        return AvroGpsWaypoint.newBuilder()
            .setId(gpsWaypoint.getId())
            .setLatitude(gpsWaypoint.getLatitude())
            .setLongitude(gpsWaypoint.getLongitude())
            .setHeading(gpsWaypoint.getHeading())
            .setSpeed(gpsWaypoint.getSpeed())
            .setTimestamp(gpsWaypoint.getTimestamp().getEpochSecond())
            .build();
    }

}
