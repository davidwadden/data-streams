package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class StreamController {

    StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping("/ingest")
    public void ingest(@RequestBody IngestedPayload ingestedPayload) {

        streamService.ingestPayload(ingestedPayload);
    }

}
