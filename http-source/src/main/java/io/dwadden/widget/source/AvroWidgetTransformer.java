package io.dwadden.widget.source;

import io.dwadden.widget.avro.AvroWidget;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class AvroWidgetTransformer {

    static Mapper dozerBeanMapper = new DozerBeanMapper();

    public AvroWidget transform(Widget widget) {

        return dozerBeanMapper.map(widget, AvroWidget.class);
    }

}
