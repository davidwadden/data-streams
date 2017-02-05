package io.dwadden.gps.avrosource;

import io.dwadden.gps.entities.AvroTripSummary;
import io.dwadden.gps.entities.TripSummary;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AvroTripSummaryTransformerTest {

    AvroTripSummaryTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new AvroTripSummaryTransformer();
    }

    @DisplayName("should convert from JSON to Avro")
    @Test
    void transform() {

        List<TripSummary.GyroDetail> gyroDetails;

        TripSummary.GyroDetail gyroDetail1 = TripSummary.GyroDetail.builder()
            .pitch(1.23)
            .roll(2.24)
            .yaw(3.34)
            .build();
        TripSummary.GyroDetail gyroDetail2 = TripSummary.GyroDetail.builder()
            .pitch(2.23)
            .roll(3.24)
            .yaw(4.45)
            .build();

        TripSummary tripSummary = TripSummary.builder()
            .id(1234L)
            .symbol(TripSummary.SymbolEnum.B)
            .gyros(asList(gyroDetail1, gyroDetail2))
            .build();

        Message<TripSummary> inMsg = MessageBuilder.withPayload(tripSummary).build();

        Message<AvroTripSummary> outMsg = transformer.transform(inMsg);

//        assertThat(outMsg.getPayload()).isEqualToComparingFieldByFieldRecursively(inMsg.getPayload());
    }

}
