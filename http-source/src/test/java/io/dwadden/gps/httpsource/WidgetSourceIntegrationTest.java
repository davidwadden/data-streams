package io.dwadden.gps.httpsource;

import io.dwadden.gps.entities.AvroWidget;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class WidgetSourceIntegrationTest {

    final Source source;
    final MessageCollector collector;

    @Autowired
    WidgetSourceIntegrationTest(Source source, MessageCollector collector) {
        this.source = source;
        this.collector = collector;
    }

    @SneakyThrows(InterruptedException.class)
    @DisplayName("should integration test the source")
    @Test
    void ingest() {
        Message<?> msg = collector.forChannel(source.output()).poll(1, TimeUnit.SECONDS);

        assertThat(msg).isNotNull();
        assertThat(msg.getPayload()).isExactlyInstanceOf(AvroWidget.class);

        AvroWidget widget = (AvroWidget) msg.getPayload();
        assertThat(widget.getKey()).isEqualTo(0);
    }
}
