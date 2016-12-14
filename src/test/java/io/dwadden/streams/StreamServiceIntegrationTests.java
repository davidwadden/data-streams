package io.dwadden.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class StreamServiceIntegrationTests {

    final StreamService streamService;
    final Source source;
    final MessageCollector collector;

    @Autowired
    StreamServiceIntegrationTests(StreamService streamService, Source source, MessageCollector collector) {
        this.streamService = streamService;
        this.source = source;
        this.collector = collector;
    }

    @SuppressWarnings("unchecked")
    @DisplayName("should integration test the source")
    @Test
    void ingest() {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        streamService.ingestPayload(ingestedPayload);

        assertThat(collector.forChannel(source.output()),
            receivesPayloadThat(equalTo(ingestedPayload)).within(1, TimeUnit.SECONDS));
    }
}
