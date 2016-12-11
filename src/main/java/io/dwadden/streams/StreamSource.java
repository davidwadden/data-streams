package io.dwadden.streams;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableBinding(Source.class)
public class StreamSource {

    static ObjectMapper objectMapper = new ObjectMapper();

    MessageChannel output;

    @Autowired
    public StreamSource(MessageChannel output) {
        this.output = output;
    }

    void ingest(IngestedPayload ingestedPayload) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(ingestedPayload);
        Message<String> message = MessageBuilder
            .withPayload(payload)
            .build();

        output.send(message);
    }

}
