package io.dwadden.data.source;

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
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.verify;

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
    FakeGatewayProperties fakeGatewayProperties;

    MockRestServiceServer mockServer;
    FakeGateway fakeGateway;


    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        fakeGateway = new FakeGateway(fakeGatewayProperties, restTemplate);
    }

    @DisplayName("should upload the payload to the endpoint")
    @Test
    void uploadPayload() {
        fakeGatewayProperties.setEndpoint("http://some-host/some-endpoint");

        mockServer.expect(MockRestRequestMatchers.requestTo("http://some-host/some-endpoint"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andRespond(MockRestResponseCreators.withNoContent());

        fakeGateway.uploadPayload();

        mockServer.verify();

        verify(fakeGatewayProperties).getEndpoint();
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
