package io.dwadden.streams;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("unused")
@EnableScheduling
@Configuration
public class StreamsExampleConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
