package io.dwadden.streams;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;

@SuppressWarnings("unused")
@EnableBinding(Source.class)
public class SecondSource {

    @InboundChannelAdapter(value = Source.OUTPUT)
    public IngestedPayload payloadSource() {
        return IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();
    }

}