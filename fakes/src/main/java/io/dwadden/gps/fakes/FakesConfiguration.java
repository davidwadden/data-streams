package io.dwadden.gps.fakes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("unused")
@Configuration
public class FakesConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

}
