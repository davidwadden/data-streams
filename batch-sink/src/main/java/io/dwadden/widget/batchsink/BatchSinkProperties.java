package io.dwadden.widget.batchsink;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("unused")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties
public class BatchSinkProperties {

    Integer batchSize;

}
