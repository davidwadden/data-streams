package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
class WidgetBatchFileHandlerTest {

    WidgetBatchFileHandler handler;
    MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        handler = new WidgetBatchFileHandler(restTemplate);
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void handleMessage() {
        server
            .expect(requestTo("http://localhost:8080/upload"))
            .andExpect(
                content().string("some-body")
            )
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        AvroWidget avroWidget = AvroWidget.newBuilder()
            .setKey(12345L)
            .setType("some-type")
            .setPayload("some-payload")
            .build();
        Message<AvroWidget> avroMessage = MessageBuilder.withPayload(avroWidget).build();

        handler.handleMessage(avroMessage);

        server.verify();
    }

}
