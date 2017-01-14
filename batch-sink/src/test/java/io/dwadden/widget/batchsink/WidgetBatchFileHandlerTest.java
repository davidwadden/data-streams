package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import reactor.test.publisher.TestPublisher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
class WidgetBatchFileHandlerTest {

    static final String API_ENDPOINT = "http://some.api/endpoint";

    BatchSinkProperties properties;
    MockRestServiceServer mockServer;
    TestPublisher<Message<AvroWidget>> testPublisher;
    WidgetBatchFileHandler handler;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        testPublisher = TestPublisher.create();

        properties = new BatchSinkProperties();
        properties.setBatchSize(3);
        properties.setUploadEndpoint(API_ENDPOINT);

        handler = new WidgetBatchFileHandler(restTemplate, properties, testPublisher);
    }

    @DisplayName("should handle widget and make HTTP API call")
    @Test
    void handleMessage() {
        mockServer
            .expect(requestTo(API_ENDPOINT))
            .andExpect(content().string("12345"))
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        testPublisher.next(makeAvroMessage());

        mockServer.verify();
    }

    private static Message<AvroWidget> makeAvroMessage() {
        AvroWidget avroWidget = AvroWidget.newBuilder()
            .setKey(12345L)
            .setType("some-type")
            .setPayload("some-payload")
            .build();
        return MessageBuilder
            .withPayload(avroWidget)
            .build();
    }

}
