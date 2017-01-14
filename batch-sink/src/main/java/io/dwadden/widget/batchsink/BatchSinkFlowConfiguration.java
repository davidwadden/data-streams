package io.dwadden.widget.batchsink;

import io.dwadden.widget.avro.AvroWidget;
import org.reactivestreams.Publisher;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.Message;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Sink.class)
@Configuration
public class BatchSinkFlowConfiguration {

    @Bean
    public Publisher<Message<AvroWidget>> reactiveFlow() {
        return IntegrationFlows
            .from(Sink.INPUT)
            .channel(MessageChannels.reactive())
            .toReactivePublisher();
    }

}
