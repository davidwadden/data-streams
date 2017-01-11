package io.dwadden.widget.batchsink;

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
class UploadControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UploadController controller = new UploadController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @SneakyThrows
    @DisplayName("uploads to the controller")
    @Test
    void upload() {
        mockMvc
            .perform(
                post("/upload")
                .content("upload-content")
            )
            .andExpect(status().isNoContent())
            .andReturn();
    }

}
