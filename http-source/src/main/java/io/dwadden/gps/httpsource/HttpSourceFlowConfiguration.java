package io.dwadden.gps.httpsource;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Source.class)
@Configuration
public class HttpSourceFlowConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public MessageSource<?> longMessageSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(new AtomicLong());
        source.setMethodName("getAndIncrement");
        return source;
    }

    @Bean
    public IntegrationFlow flow(WidgetGenerator widgetGenerator,
                                WidgetTransformer widgetTransformer) {

        return IntegrationFlows
            .from(this.longMessageSource(), c -> c.poller(Pollers.fixedRate(100L)))
            .transform(widgetGenerator)
            .transform(widgetTransformer)
            .channel(Source.OUTPUT)
            .get();
    }

}
