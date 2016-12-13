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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StreamSourceTests {

    static final ObjectMapper objectMapper = new ObjectMapper();

    @Mock MessageChannel messageChannel;
    StreamSource streamSource;

    @BeforeEach
    void setUp() {
        initMocks(this);
        streamSource = new StreamSource(messageChannel);
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows(JsonProcessingException.class)
    @DisplayName("should send the message with a payload of ingested payload")
    @Test
    void ingest() {
        when(messageChannel.send(any())).thenReturn(true);

        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();
        String payload = objectMapper.writeValueAsString(ingestedPayload);

        streamSource.ingest(payload);

        ArgumentCaptor<Message<String>> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageChannel).send(argumentCaptor.capture());
        Message<String> sentMessage = argumentCaptor.getValue();

        assertThat(sentMessage.getPayload()).isEqualTo(payload);
    }

    @DisplayName("when the message channel fails in a non-fatal manner")
    @Nested
    class FailingMessageChannelTests {

        @SneakyThrows(JsonProcessingException.class)
        @DisplayName("should put the message onto the errorChannel")
        @Test
        void ingest_channelFails() {
            when(messageChannel.send(any())).thenReturn(false);

            IngestedPayload ingestedPayload = IngestedPayload.builder()
                .type("some-type")
                .payload("some-payload")
                .build();
            String payload = objectMapper.writeValueAsString(ingestedPayload);

            streamSource.ingest(payload);

            verify(messageChannel).send(any());
        }
    }
}
