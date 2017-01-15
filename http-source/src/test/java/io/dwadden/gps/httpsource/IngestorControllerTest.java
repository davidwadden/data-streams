package io.dwadden.gps.httpsource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dwadden.gps.entities.RawGpsWaypoint;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class IngestorControllerTest {

    static final ObjectMapper objectMapper = makeObjectMapper();

    @Mock MessageChannel channel;
    @Captor ArgumentCaptor<Message<?>> waypointCaptor;
    @InjectMocks IngestorController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(makeMessageConverter())
            .build();
    }

    @SneakyThrows
    @DisplayName("should respond to HTTP POST requests")
    @Test
    void ingest() {
        RawGpsWaypoint expected = makeRawGpsWaypoint();
        String waypointJson = makeWaypointJson();
        logger.info(waypointJson);

        mockMvc
            .perform(
                post("/ingest")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .content(waypointJson)
            )
            .andExpect(status().isAccepted())
            .andReturn();

        verify(channel).send(waypointCaptor.capture());
        Message<?> msg = waypointCaptor.getValue();
        assertThat(msg.getPayload()).isExactlyInstanceOf(RawGpsWaypoint.class);

        RawGpsWaypoint rawWaypoint = (RawGpsWaypoint) msg.getPayload();
        assertThat(rawWaypoint).isEqualTo(expected);
    }

    @DisplayName("when the server fails for some reason")
    @Nested
    class ErrorTests {

        @Disabled
        @SneakyThrows
        @DisplayName("should return 400 Bad Request for invalid request")
        @Test
        void ingest_badRequest() {
            MvcResult mvcResult = mockMvc
                .perform(
                    post("/ingest")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                        .content("some-invalid-content")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

            verifyZeroInteractions(channel);
        }

        @Disabled
        @SneakyThrows
        @DisplayName("should return 500 Internal Server Error when exception thrown")
        @Test
        void ingest_internalServerError() {
            when(channel.send(any(Message.class))).thenThrow(new RuntimeException("some-exception"));
            String waypointJson = makeWaypointJson();

            MvcResult mvcResult = mockMvc
                .perform(
                    post("/ingest")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(waypointJson)
                )
                .andExpect(status().isInternalServerError())
                .andReturn();

            assertThatJson(mvcResult.getResponse().getContentAsString())
                .node("status")
                .isEqualTo("failure");
        }
    }

    @SneakyThrows(JsonProcessingException.class)
    private static String makeWaypointJson() {
        return objectMapper
            .writer()
            .withDefaultPrettyPrinter()
            .writeValueAsString(makeRawGpsWaypoint());
    }

    private static RawGpsWaypoint makeRawGpsWaypoint() {
        return RawGpsWaypoint.builder()
            .latitude(41.921855d)
            .longitude(-87.633487d)
            .heading(290)
            .speed(72)
            .timestamp(Instant.ofEpochSecond(1484466954L))
            .build();
    }

    private static ObjectMapper makeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    private static MappingJackson2HttpMessageConverter makeMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(makeObjectMapper());
        return converter;
    }

}
