package io.dwadden.gps.httpapisink;

import io.dwadden.gps.entities.AvroGpsWaypoint;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Sink.class)
@EnableConfigurationProperties(HttpApiSinkProperties.class)
@Configuration
public class HttpApiSinkFlowConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public Publisher<Message<AvroGpsWaypoint>> reactiveFlow() {
        return IntegrationFlows
            .from(Sink.INPUT)
            .channel(MessageChannels.reactive())
            .toReactivePublisher();
    }

}
