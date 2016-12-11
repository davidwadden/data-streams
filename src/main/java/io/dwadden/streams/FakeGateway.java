package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Component
public class FakeGateway {

    RestOperations restOperations;

    @Value("${fakeGateway.endpoint}")
    @NonFinal
    String endpoint;

    @Autowired
    public FakeGateway(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Scheduled(fixedDelayString = "${fakeGateway.fixedDelay}")
    public void uploadPayload() throws URISyntaxException {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        RequestEntity<IngestedPayload> requestEntity = RequestEntity
            .post(new URI("http://localhost:8080/ingest"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(ingestedPayload);

        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);

        logger.info("upload response: {}", responseEntity.getStatusCode().name());
    }
}
