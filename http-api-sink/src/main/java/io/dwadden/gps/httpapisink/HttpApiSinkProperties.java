package io.dwadden.gps.httpapisink;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties(prefix = "httpApiSink")
public class HttpApiSinkProperties {

    Integer batchSize;
    String uploadEndpoint;

}
