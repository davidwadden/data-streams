package io.dwadden.widget.source;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class WidgetFactoryTransformerTest {

    WidgetFactoryTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new WidgetFactoryTransformer();
    }

    @Test
    void transform() {
        Widget widget = transformer.transform(47L);

        assertThat(widget.getKey()).isEqualTo(47L);
        assertThat(widget.getType()).isEqualTo("some-type");
        assertThat(widget.getPayload()).isEqualTo("some-payload");
    }

}
