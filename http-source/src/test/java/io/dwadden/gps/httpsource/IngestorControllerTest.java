package io.dwadden.gps.httpsource;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
class IngestorControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        IngestorController controller = new IngestorController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @SneakyThrows
    @DisplayName("should respond to HTTP POST requests")
    @Test
    void ingest() {
        mockMvc
            .perform(
                post("/ingest").content("some-request")
            )
            .andExpect(status().isAccepted())
            .andReturn();

    }

}
