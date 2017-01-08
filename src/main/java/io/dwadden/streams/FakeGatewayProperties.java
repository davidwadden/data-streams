package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EnableScheduling
@ConfigurationProperties(prefix = "fakeGateway")
public class FakeGatewayProperties {

    String fixedDelay;
    String endpoint;

}
