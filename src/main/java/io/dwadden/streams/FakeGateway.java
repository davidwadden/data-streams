package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@SuppressWarnings("unused")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Component
public class FakeGateway {

    RestOperations restOperations;

    public FakeGateway(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Scheduled(fixedDelayString = "${fakeGateway.fixedDelay}")
    public void uploadPayload() {
        logger.info("uploading fake payload");
    }
}
