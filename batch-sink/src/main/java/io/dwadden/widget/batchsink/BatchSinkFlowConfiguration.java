package io.dwadden.widget.batchsink;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Sink.class)
@Configuration
public class BatchSinkFlowConfiguration {

    @Bean
    public IntegrationFlow flow(Sink input, WidgetBatchFileHandler widgetBatchFileHandler) {
        return IntegrationFlows
            .from(Sink.INPUT)
            .handle(widgetBatchFileHandler)
            .get();
    }

}
