package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@SuppressWarnings("unused")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@EnableConfigurationProperties
@Component
public class FakeGateway {

    FakeGatewayConfiguration fakeGatewayConfiguration;
    RestOperations restOperations;

    @Autowired
    public FakeGateway(FakeGatewayConfiguration fakeGatewayConfiguration,
                       RestOperations restOperations) {
        this.fakeGatewayConfiguration = fakeGatewayConfiguration;
        this.restOperations = restOperations;
    }

    @Scheduled(initialDelay = 1_000, fixedDelayString = "${fakeGateway.fixedDelay}")
    public void uploadPayload() {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        RequestEntity<IngestedPayload> requestEntity = RequestEntity
            .post(URI.create(fakeGatewayConfiguration.getEndpoint()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(ingestedPayload);

        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);

        logger.info("upload response: {}", responseEntity.getStatusCode().name());
    }
}
