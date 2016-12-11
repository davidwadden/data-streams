package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@FieldDefaults(level = AccessLevel.PRIVATE)
class FakeGatewayTest {

    MockRestServiceServer mockServer;
    FakeGateway fakeGateway;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        fakeGateway = new FakeGateway(restTemplate);
    }

    @DisplayName("should upload the payload to the endpoint")
    @Test
    void uploadPayload() throws URISyntaxException {
        mockServer.expect(requestTo("http://localhost:8080/ingest"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(MockRestResponseCreators.withNoContent());

        fakeGateway.uploadPayload();

        mockServer.verify();
    }

}
