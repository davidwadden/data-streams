package io.dwadden.gps.fakes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties(prefix = "httpGateway")
public class GpsWaypointHttpGatewayProperties {

    Integer initialDelay;
    Integer fixedDelay;
    String endpoint;

}
