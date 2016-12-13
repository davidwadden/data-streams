package io.dwadden.streams;


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

    MessageChannel output;

    @Autowired
    public StreamSource(MessageChannel output) {
        this.output = output;
    }

    void ingest(String payload) {
        Message<String> message = MessageBuilder
            .withPayload(payload)
            .build();

        output.send(message);
    }

}
