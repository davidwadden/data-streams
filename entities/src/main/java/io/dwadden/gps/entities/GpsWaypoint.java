package io.dwadden.gps.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = GpsWaypoint.GpsWaypointBuilder.class)
@Builder
@Value
public class GpsWaypoint {

    Long key;
    String type;
    String payload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GpsWaypointBuilder {}

}
