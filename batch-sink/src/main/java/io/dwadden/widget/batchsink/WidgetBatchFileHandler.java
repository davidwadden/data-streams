package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@SuppressWarnings("unused")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class WidgetBatchFileHandler {

    RestOperations restOperations;
    BatchSinkProperties batchSinkProperties;

    public WidgetBatchFileHandler(RestOperations restOperations, BatchSinkProperties batchSinkProperties) {
        this.restOperations = restOperations;
        this.batchSinkProperties = batchSinkProperties;
    }

    public void handleMessage(Message<AvroWidget> message) {
        logger.info(message.getPayload().toString());

        restOperations.postForEntity(
            batchSinkProperties.getUploadEndpoint(),
            message.getPayload().getKey().toString(),
            null
        );
    }

}
