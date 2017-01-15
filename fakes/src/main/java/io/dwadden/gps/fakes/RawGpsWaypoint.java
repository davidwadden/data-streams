package io.dwadden.gps.fakes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class RawGpsWaypoint {

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

}
