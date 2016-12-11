package io.dwadden.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class StreamServiceTest {

    @Mock StreamSource streamSource;
    StreamService streamService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        streamService = new StreamService(streamSource);
    }

    @DisplayName("should send the ingested payload to the stream source")
    @Test
    void ingestPayload() throws JsonProcessingException {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        streamService.ingestPayload(ingestedPayload);

        verify(streamSource).ingest(ingestedPayload);
    }

}