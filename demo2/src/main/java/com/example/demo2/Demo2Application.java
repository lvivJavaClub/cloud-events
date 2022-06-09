package com.example.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    @Bean
    KafkaListenerConfigurer configListeners(ObjectMapper objectMapper) {

        var cloudEventMessageConverter = new MessageConverter() {

            @SneakyThrows
            @Override
            public Object fromMessage(Message<?> message, Class<?> targetClass) {
                var cloudEvent = (CloudEvent) message.getPayload();
                return objectMapper.readValue(cloudEvent.getData().toBytes(), targetClass);
            }

            @Override
            public Message<?> toMessage(Object payload, MessageHeaders headers) {
                return null;
            }
        } ;


        return  registrar -> {
            registrar.setCustomMethodArgumentResolvers(
                new PayloadMethodArgumentResolver(cloudEventMessageConverter)
            );
        };
    }


}
