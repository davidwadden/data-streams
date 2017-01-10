package io.dwadden.widget.source;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;

@SuppressWarnings("unused")
@EnableBinding(Source.class)
public class WidgetSource {

    @InboundChannelAdapter(value = Source.OUTPUT)
    public Widget payloadSource() {
        return Widget.builder()
            .type("some-type")
            .payload("some-payload")
            .build();
    }

}
