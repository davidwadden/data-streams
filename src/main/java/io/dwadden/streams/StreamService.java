package io.dwadden.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class StreamService {

    StreamSource streamSource;

    @Autowired
    public StreamService(StreamSource streamSource) {
        this.streamSource = streamSource;
    }

    public void ingestPayload(IngestedPayload ingestedPayload) throws JsonProcessingException {
        streamSource.ingest(ingestedPayload);
    }

}
