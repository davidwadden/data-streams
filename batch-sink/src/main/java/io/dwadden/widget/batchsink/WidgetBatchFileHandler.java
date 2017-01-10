package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Slf4j
@Component
public class WidgetBatchFileHandler {

    public void handleMessage(Message<AvroWidget> message) {
        logger.info(message.getPayload().toString());
    }

}
