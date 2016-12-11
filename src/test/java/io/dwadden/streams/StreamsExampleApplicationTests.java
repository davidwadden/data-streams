package io.dwadden.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StreamsExampleApplicationTests {

    static ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unused")
    @Autowired
    TestRestTemplate testRestTemplate;

    @DisplayName("should autowire successfully")
    @Test
    public void contextLoads() { }

    @DisplayName("should ingest HTTP payload into Kafka topic")
    @Test
    public void ingestPayload() throws JsonProcessingException {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();
        String postBody = objectMapper.writeValueAsString(ingestedPayload);

        RequestEntity<String> requestEntity = RequestEntity
            .post(URI.create("/ingest"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(postBody);

        ResponseEntity<String> responseEntity =
            testRestTemplate.postForEntity("/ingest", requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

}
