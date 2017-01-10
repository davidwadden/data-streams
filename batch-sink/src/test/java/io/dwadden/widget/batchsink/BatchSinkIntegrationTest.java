package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BatchSinkIntegrationTest {

    final Sink sink;

    @Autowired
    BatchSinkIntegrationTest(Sink sink) {
        this.sink = sink;
    }

    @DisplayName("should test the Batch Sink integrates properly")
    @Test
    void batchSink() {
        AvroWidget avroWidget = AvroWidget.newBuilder()
            .setKey(12345L)
            .setType("some-type")
            .setPayload("some-payload")
            .build();
        Message<AvroWidget> message = MessageBuilder.withPayload(avroWidget).build();

        sink.input().send(message);
    }

}
