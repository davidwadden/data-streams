package io.dwadden.gps.httpapisink;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("unused")
@EnableConfigurationProperties(BatchSinkProperties.class)
@Configuration
public class BatchSinkConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

}
