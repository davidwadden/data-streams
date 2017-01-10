package io.dwadden.widget.source;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AvroWidgetTransformerTest {

    AvroWidgetTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new AvroWidgetTransformer();
    }

    @Test
    void transform() {
        Widget widget = Widget.builder()
            .key(1004L)
            .type("some-type")
            .payload("some-payload")
            .build();

        AvroWidget avroWidget = transformer.transform(widget);

        assertThat(avroWidget.getKey()).isEqualTo(1004L);
        assertThat(avroWidget.getType()).isEqualTo("some-type");
        assertThat(avroWidget.getPayload()).isEqualTo("some-payload");
    }

}
