package io.dwadden.widget.source;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = Widget.IngestedPayloadBuilder.class)
@Builder
@Value
public class Widget {

    Long key;
    String type;
    String payload;

    @JsonPOJOBuilder(withPrefix = "")
    public static class IngestedPayloadBuilder {}

}
