package io.dwadden.gps.httpsource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@Slf4j
@RestController
public class IngestorController {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/ingest", method = RequestMethod.POST)
    public void ingest(@RequestBody String body) {
      logger.info("received request to ingest");
    }

}
