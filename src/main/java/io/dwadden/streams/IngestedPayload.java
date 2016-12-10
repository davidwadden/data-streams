package io.dwadden.streams;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = IngestedPayload.IngestedPayloadBuilder.class)
@Builder
@Value
public class IngestedPayload {

    String type;
    String payload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class IngestedPayloadBuilder {}

}
