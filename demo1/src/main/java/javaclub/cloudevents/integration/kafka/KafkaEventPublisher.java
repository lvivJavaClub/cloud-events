package javaclub.cloudevents.integration.kafka;

import java.net.URI;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.core.message.Encoding;
import io.cloudevents.jackson.JsonFormat;
import io.cloudevents.kafka.CloudEventSerializer;
import javaclub.cloudevents.integration.event.EventPublisher;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaEventPublisher implements EventPublisher {

    KafkaProducer<String, CloudEvent> kafkaProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    public KafkaEventPublisher() {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CloudEventSerializer.class);
//        properties.put(CloudEventSerializer.ENCODING_CONFIG, Encoding.STRUCTURED);
        properties.put(CloudEventSerializer.EVENT_FORMAT_CONFIG, JsonFormat.CONTENT_TYPE);

        kafkaProducer = new KafkaProducer<String, CloudEvent>(properties);
    }

    @SneakyThrows
    @Override
    public <T> void publishEvent(T event) {
        var data = PojoCloudEventData.wrap(event, objectMapper::writeValueAsBytes);

        var cloudEvent = CloudEventBuilder.v1()
            .withId(UUID.randomUUID().toString())
            .withSource(URI.create("CustomerService"))
            .withType(event.getClass().getSimpleName())
            .withData("application/json", data)
            .build();

        var res = kafkaProducer.send(new ProducerRecord<>("my.topic3", cloudEvent));
        var recordMetadata = res.get();
        System.out.println("recordMetadata = " + recordMetadata);

    }


}
