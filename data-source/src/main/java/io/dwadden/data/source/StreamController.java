package io.dwadden.data.source;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
public class StreamController {

    StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/ingest", method = RequestMethod.POST)
    public void ingest(@RequestBody IngestedPayload ingestedPayload) {

        streamService.ingestPayload(ingestedPayload);
    }

}
