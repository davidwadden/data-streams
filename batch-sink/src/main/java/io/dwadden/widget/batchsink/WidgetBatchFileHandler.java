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

    public WidgetBatchFileHandler(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public void handleMessage(Message<AvroWidget> message) {
        logger.info(message.getPayload().toString());

        restOperations.postForEntity(
            "http://localhost:8080/upload",
            message.getPayload().getKey().toString(),
            null
        );
    }

}
