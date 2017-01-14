package io.dwadden.widget.batchsink;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties(prefix = "batchSink")
public class BatchSinkProperties {

    Integer batchSize;
    String uploadEndpoint;

}
