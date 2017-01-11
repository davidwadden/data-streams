package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BatchSinkIntegrationTest {

    final Sink sink;
    final RestOperations restOperations;
    MockRestServiceServer mockServer;

    @Autowired
    BatchSinkIntegrationTest(Sink sink, RestOperations restOperations) {
        this.sink = sink;
        this.restOperations = restOperations;
    }

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer((RestTemplate) restOperations);
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

        mockServer
            .expect(requestTo("http://localhost:8080/upload"))
            .andExpect(content().string("12345"))
            .andRespond(withStatus(HttpStatus.NO_CONTENT));

        sink.input().send(message);

        mockServer.verify();
    }

}
