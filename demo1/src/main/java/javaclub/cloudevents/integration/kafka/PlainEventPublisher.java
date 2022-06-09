package javaclub.cloudevents.integration.kafka;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import javaclub.cloudevents.integration.event.EventPublisher;

public class PlainEventPublisher implements EventPublisher {

    EventFormat jsonFormat = EventFormatProvider
        .getInstance()
        .resolveFormat(JsonFormat.CONTENT_TYPE);

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> void publishEvent(T event) {

        var data = PojoCloudEventData.wrap(event, objectMapper::writeValueAsBytes);

        var cloudEvent = CloudEventBuilder.v1()
            .withId(UUID.randomUUID().toString())
            .withSource(URI.create("CustomerService"))
            .withType(event.getClass().getSimpleName())
            .withData("application/json", data)
            .build();

        var bytes = jsonFormat.serialize(cloudEvent);
        System.out.println("cloudEvent = " + new String(bytes));
    }

}
