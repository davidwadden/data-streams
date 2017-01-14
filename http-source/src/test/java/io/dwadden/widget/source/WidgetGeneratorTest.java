package io.dwadden.widget.source;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class WidgetGeneratorTest {

    WidgetGenerator transformer;

    @BeforeEach
    void setUp() {
        transformer = new WidgetGenerator();
    }

    @DisplayName("should transform from a key to a widget")
    @Test
    void transform() {
        Widget widget = transformer.transform(47L);

        assertThat(widget.getKey()).isEqualTo(47L);
        assertThat(widget.getType()).isEqualTo("some-type");
        assertThat(widget.getPayload()).isEqualTo("some-payload");
    }

}
