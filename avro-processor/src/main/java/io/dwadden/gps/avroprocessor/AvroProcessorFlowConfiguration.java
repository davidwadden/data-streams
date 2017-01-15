package io.dwadden.gps.avroprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Processor.class)
@Configuration
public class AvroProcessorFlowConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    public IntegrationFlow flow(JsonToAvroTransformer serializeAvroTransformer) {

        return IntegrationFlows
            .from(Processor.INPUT)
            .transform(serializeAvroTransformer)
            .channel(Processor.OUTPUT)
            .get();
    }

}
