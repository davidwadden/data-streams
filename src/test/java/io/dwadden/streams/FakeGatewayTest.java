package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ContextConfiguration(classes = {
    FakeGateway.class,
    FakeGatewayTest.ContextConfiguration.class,
})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FakeGatewayTest {

    @SuppressWarnings("unused")
    @SpyBean
    FakeGatewayConfiguration fakeGatewayConfiguration;

    MockRestServiceServer mockServer;
    FakeGateway fakeGateway;


    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        fakeGateway = new FakeGateway(fakeGatewayConfiguration, restTemplate);
    }

    @DisplayName("should upload the payload to the endpoint")
    @Test
    void uploadPayload() throws URISyntaxException {
        fakeGatewayConfiguration.setEndpoint("http://some-host/some-endpoint");

        mockServer.expect(requestTo("http://some-host/some-endpoint"))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withNoContent());

        fakeGateway.uploadPayload();

        mockServer.verify();

        verify(fakeGatewayConfiguration).getEndpoint();
    }

    @SuppressWarnings("unused")
    @EnableWebMvc
    @Configuration
    static class ContextConfiguration {

        @Bean
        public RestOperations restOperations() {
            return new RestTemplate();
        }

    }

}
