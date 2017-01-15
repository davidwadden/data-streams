package io.dwadden.gps.httpsource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("unused")
@EnableIntegration
@EnableBinding(Source.class)
@Configuration
public class HttpSourceFlowConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectToJsonTransformer objectToJsonTransformer(ObjectMapper objectMapper) {
        return Transformers.toJson(new Jackson2JsonObjectMapper(objectMapper));
    }

    @Bean
    public MessageChannel listenHttpChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow flow(MessageChannel listenHttpChannel,
                                ObjectToJsonTransformer objectToJsonTransformer,
                                AssignKeyTransformer assignKeyTransformer) {

        return IntegrationFlows
            .from(listenHttpChannel)
            .transform(assignKeyTransformer)
            .transform(objectToJsonTransformer)
            .channel(Source.OUTPUT)
            .get();
    }

}
