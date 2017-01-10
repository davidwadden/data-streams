package io.dwadden.data.source;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
class DataSourceTest {

    DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DataSource();
    }

    @DisplayName("should put message on topic with fixed payload")
    @Test
    void payloadSource() {
        IngestedPayload ingestedPayload = dataSource.payloadSource();

        IngestedPayload expected = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        assertThat(ingestedPayload).isEqualTo(expected);
    }

}
