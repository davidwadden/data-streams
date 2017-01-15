package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class IngestorController {

    MessageChannel listenHttpChannel;

    @Autowired
    public IngestorController(MessageChannel listenHttpChannel) {
        this.listenHttpChannel = listenHttpChannel;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/ingest", method = RequestMethod.POST)
    public void ingest(@RequestBody RawGpsWaypoint rawWaypoint) {
        logger.info("received request to ingest");

        Message<RawGpsWaypoint> msg = MessageBuilder
            .withPayload(rawWaypoint)
            .build();

        listenHttpChannel.send(msg);
    }

}
