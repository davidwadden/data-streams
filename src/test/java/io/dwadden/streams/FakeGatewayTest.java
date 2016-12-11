package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FakeGatewayTest {

    @SuppressWarnings("unused")
    @SpyBean
    StreamsExampleConfiguration configuration;

    MockRestServiceServer mockServer;
    FakeGateway fakeGateway;


    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        fakeGateway = new FakeGateway(configuration, restTemplate);
    }

    @DisplayName("should upload the payload to the endpoint")
    @Test
    void uploadPayload() throws URISyntaxException {
        configuration.setFakeGatewayEndpoint("http://some-host/some-endpoint");

        mockServer.expect(requestTo("http://some-host/some-endpoint"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(MockRestResponseCreators.withNoContent());

        fakeGateway.uploadPayload();

        mockServer.verify();

        verify(configuration, times(2)).getFakeGatewayEndpoint();
    }

}
