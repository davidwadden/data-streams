package io.dwadden.widget.source;

import org.springframework.stereotype.Component;

@Component
public class WidgetGenerator {

    public Widget transform(Long key) {

        return Widget.builder()
            .key(key)
            .type("some-type")
            .payload("some-payload")
            .build();
    }
}
