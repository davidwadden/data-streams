package io.dwadden.gps.httpsource;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class AtomicLongIdGenerator implements IdGenerator<Long> {

    AtomicLong atomicLong = new AtomicLong();

    @Override
    public Long generateId() {
        return atomicLong.incrementAndGet();
    }

}
