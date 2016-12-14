package io.dwadden.streams;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class SecondSourceTests {

    SecondSource secondSource;

    @BeforeEach
    void setUp() {
        secondSource = new SecondSource();
    }

    @Test
    void payloadSource() {
        IngestedPayload ingestedPayload = secondSource.payloadSource();

        IngestedPayload expected = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();
        assertThat(ingestedPayload).isEqualTo(expected);
    }

}
