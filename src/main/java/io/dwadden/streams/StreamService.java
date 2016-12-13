package io.dwadden.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class StreamService {

    static ObjectMapper objectMapper = new ObjectMapper();

    StreamSource streamSource;

    @Autowired
    public StreamService(StreamSource streamSource) {
        this.streamSource = streamSource;
    }

    @SneakyThrows(JsonProcessingException.class)
    public void ingestPayload(IngestedPayload ingestedPayload) {
        String payload = objectMapper.writeValueAsString(ingestedPayload);

        streamSource.ingest(payload);
    }

}
