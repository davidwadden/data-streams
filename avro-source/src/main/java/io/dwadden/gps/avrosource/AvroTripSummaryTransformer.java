package io.dwadden.gps.avrosource;

import io.dwadden.gps.entities.AvroTripSummary;
import io.dwadden.gps.entities.TripSummary;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.dozer.DozerBeanMapper;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class AvroTripSummaryTransformer {

    static DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public Message<AvroTripSummary> transform(Message<TripSummary> tripSummaryMessage) {

        TripSummary ts = tripSummaryMessage.getPayload();
        AvroTripSummary ats = dozerBeanMapper.map(ts, AvroTripSummary.class);

        return MessageBuilder
            .withPayload(ats)
            .build();
    }

}
