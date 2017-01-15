package io.dwadden.gps.httpsource;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AtomicLongIdGeneratorTest {

    AtomicLongIdGenerator idGenerator;

    @BeforeEach
    void setUp() {
        idGenerator = new AtomicLongIdGenerator();
    }

    @DisplayName("should generate unique sequential keys")
    @Test
    void generateId() {
        Long id1 = idGenerator.generateId();
        assertThat(id1).isEqualTo(1L);

        Long id2 = idGenerator.generateId();
        assertThat(id2).isEqualTo(2L);
    }

}
