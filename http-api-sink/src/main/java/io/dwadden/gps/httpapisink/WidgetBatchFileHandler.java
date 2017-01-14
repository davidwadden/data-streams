package io.dwadden.gps.httpapisink;

import io.dwadden.gps.entities.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import reactor.core.publisher.Flux;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class WidgetBatchFileHandler {

    RestOperations restOperations;
    BatchSinkProperties batchSinkProperties;
    @SuppressWarnings("unused")
    Publisher<Message<AvroWidget>> publisher;

    @Autowired
    public WidgetBatchFileHandler(RestOperations restOperations,
                                  BatchSinkProperties batchSinkProperties,
                                  Publisher<Message<AvroWidget>> publisher) {
        this.restOperations = restOperations;
        this.batchSinkProperties = batchSinkProperties;
        this.publisher = publisher;

        // Sets up subscription to Publisher
        Flux.from(publisher)
            .map(Message::getPayload)
            .map(AvroWidget::getKey)
            .doOnNext(this::uploadWidget)
            .subscribe();
    }

    private void uploadWidget(Long key) {
        logger.info("making HTTP call to API");

        restOperations.postForEntity(
            batchSinkProperties.getUploadEndpoint(),
            key.toString(),
            null
        );
    }

}
