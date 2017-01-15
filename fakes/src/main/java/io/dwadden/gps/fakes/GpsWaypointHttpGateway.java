package io.dwadden.gps.fakes;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableConfigurationProperties(GpsWaypointHttpGatewayProperties.class)
@EnableScheduling
@Component
public class GpsWaypointHttpGateway {

    GpsWaypointHttpGatewayProperties properties;
    RestOperations restOperations;

    @Autowired
    public GpsWaypointHttpGateway(GpsWaypointHttpGatewayProperties properties,
                                  RestOperations restOperations) {
        this.properties = properties;
        this.restOperations = restOperations;
    }

    @Scheduled(
        initialDelayString = "${http-gateway.initial-delay}",
        fixedDelayString = "${http-gateway.fixed-delay}"
    )
    public void sendRequest() {
        logger.info("sending HTTP request from Gateway");

        restOperations.postForEntity(properties.getEndpoint(), "some-request", null);
    }

}
