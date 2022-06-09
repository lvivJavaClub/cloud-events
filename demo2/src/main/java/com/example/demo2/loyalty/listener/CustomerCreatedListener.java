package com.example.demo2.loyalty.listener;

import java.util.Map;

import com.example.demo2.loyalty.listener.dto.CustomerCreatedModel;
import com.example.demo2.loyalty.service.LoyaltyService;
import io.cloudevents.CloudEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCreatedListener {

    private final LoyaltyService loyaltyService;

    @KafkaListener(topics = "my.topic3")
    public void onCustomerCreated(
        @Payload CustomerCreatedModel event,
        @Headers  Map<String, String> headers
    ) {
        System.out.println("message = " + event);
        loyaltyService.grantBonusToCustomer(event.getCustomer().getCustomerId(), 1000);
    }

}
