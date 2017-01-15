package io.dwadden.gps.avroprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dwadden.gps.entities.AvroGpsWaypoint;
import io.dwadden.gps.entities.GpsWaypoint;
import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AvroProcessorIntegrationTest {

    static final ObjectMapper objectMapper = makeObjectMapper();

    final Sink sink;
    final Source source;
    final MessageCollector collector;

    @Autowired
    AvroProcessorIntegrationTest(Sink sink, Source source, MessageCollector collector) {
        this.sink = sink;
        this.source = source;
        this.collector = collector;
    }

    @SneakyThrows(InterruptedException.class)
    @DisplayName("tests the flow")
    @Test
    void flow() {
        sink.input().send(makeJsonMessage());

        Message<?> msg = collector.forChannel(source.output()).poll(1, TimeUnit.SECONDS);
        assertThat(msg).isNotNull();
        assertThat(msg.getPayload()).isExactlyInstanceOf(AvroGpsWaypoint.class);

        AvroGpsWaypoint avroGpsWaypoint = (AvroGpsWaypoint) msg.getPayload();
        assertThat(avroGpsWaypoint.getId()).isEqualTo(2001L);
    }

    @SneakyThrows(JsonProcessingException.class)
    private static Message<String> makeJsonMessage() {
        GpsWaypoint gpsWaypoint = GpsWaypoint.builder()
            .id(2001L)
            .latitude(41.921855d)
            .longitude(-87.633487d)
            .heading(290)
            .speed(72)
            .timestamp(Instant.ofEpochSecond(1484466954L))
            .build();

        String waypointJson = objectMapper.writeValueAsString(gpsWaypoint);

        return MessageBuilder
            .withPayload(waypointJson)
            .build();
    }

    private static ObjectMapper makeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }


}
