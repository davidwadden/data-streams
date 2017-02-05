package io.dwadden.gps.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = TripSummary.TripSummaryBuilder.class)
@Builder
@Value
public class TripSummary {

    Long id;
    SymbolEnum symbol;
    List<GyroDetail> gyros;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TripSummaryBuilder {}

    @JsonDeserialize(builder = GyroDetail.GyroDetailBuilder.class)
    @Builder
    @Value
    public static class GyroDetail {
        Double roll;
        Double pitch;
        Double yaw;
    }

    public enum SymbolEnum {
        A, B, C, D
    }

}
