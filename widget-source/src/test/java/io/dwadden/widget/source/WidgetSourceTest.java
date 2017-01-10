package io.dwadden.widget.source;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
class WidgetSourceTest {

    WidgetSource widgetSource;

    @BeforeEach
    void setUp() {
        widgetSource = new WidgetSource();
    }

    @DisplayName("should put message on topic with fixed payload")
    @Test
    void payloadSource() {
        Widget widget = widgetSource.payloadSource();

        Widget expected = Widget.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        assertThat(widget).isEqualTo(expected);
    }

}
