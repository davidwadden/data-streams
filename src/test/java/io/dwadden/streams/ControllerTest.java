package io.dwadden.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
class ControllerTest {

    static final ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Controller controller = new Controller();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void ingest() throws Exception {
        IngestedPayload ingestedPayload = IngestedPayload.builder()
            .type("some-type")
            .payload("some-payload")
            .build();

        String postBody = objectMapper.writeValueAsString(ingestedPayload);

        mockMvc.perform(post("/ingest")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postBody)
        ).andExpect(status().isAccepted());
    }

}
