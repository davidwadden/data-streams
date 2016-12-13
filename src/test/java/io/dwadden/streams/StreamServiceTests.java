package io.dwadden.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StreamServiceTests {

    static ObjectMapper objectMapper = new ObjectMapper();
    @Mock StreamSource streamSource;
    StreamService streamService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        streamService = new StreamService(streamSource);
    }

    @SneakyThrows(JsonProcessingException.class)
    @DisplayName("should send the ingested payload to the stream source")
    @Test
    void ingestPayload() {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        streamService.ingestPayload(ingestedPayload);

        String expected = objectMapper.writeValueAsString(ingestedPayload);
        verify(streamSource).ingest(expected);
    }

    @DisplayName("when the ingested payload fails to serialize")
    @Nested
    class SerializationFailsTests {

        @DisplayName("should throw when serialization of the ingested payload fails")
        @Test
        void ingestPayload_serializationFails() {
            // noop
        }

    }


}