package io.dwadden.widget.source;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;

@SuppressWarnings("unused")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Configuration
public class WidgetSourceConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

}
