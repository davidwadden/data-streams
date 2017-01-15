package io.dwadden.gps.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@JsonDeserialize(builder = GpsWaypoint.GpsWaypointBuilder.class)
@Builder
@Value
public class GpsWaypoint {

    Long id;
    Double latitude;
    Double longitude;
    Integer heading;
    Integer speed;

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'",
        timezone = "UTC"
    )
    Instant timestamp;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GpsWaypointBuilder {}

}
