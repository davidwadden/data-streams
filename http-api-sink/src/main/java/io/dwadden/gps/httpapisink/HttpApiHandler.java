package io.dwadden.gps.httpapisink;

import io.dwadden.gps.entities.AvroGpsWaypoint;
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
public class HttpApiHandler {

    RestOperations restOperations;
    HttpApiSinkProperties httpApiSinkProperties;
    @SuppressWarnings("unused")
    Publisher<Message<AvroGpsWaypoint>> publisher;

    @Autowired
    public HttpApiHandler(RestOperations restOperations,
                          HttpApiSinkProperties httpApiSinkProperties,
                          Publisher<Message<AvroGpsWaypoint>> publisher) {
        this.restOperations = restOperations;
        this.httpApiSinkProperties = httpApiSinkProperties;
        this.publisher = publisher;

        // Sets up subscription to Publisher
        Flux.from(publisher)
            .map(Message::getPayload)
            .map(AvroGpsWaypoint::getId)
            .doOnNext(this::uploadWidget)
            .subscribe();
    }

    private void uploadWidget(Long key) {
        logger.info("making HTTP call to API");

        restOperations.postForEntity(
            httpApiSinkProperties.getUploadEndpoint(),
            key.toString(),
            null
        );
    }

}
