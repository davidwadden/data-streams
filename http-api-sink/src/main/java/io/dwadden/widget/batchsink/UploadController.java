package io.dwadden.widget.batchsink;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UploadController {

    @SuppressWarnings("unused")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestBody String body) {
        logger.info(body);
    }

}
