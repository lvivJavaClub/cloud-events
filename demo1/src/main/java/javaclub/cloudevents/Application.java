package javaclub.cloudevents;

import java.util.UUID;

import javaclub.cloudevents.customer.model.Customer;
import javaclub.cloudevents.customer.service.CustomerService;
import javaclub.cloudevents.integration.kafka.KafkaEventPublisher;
import javaclub.cloudevents.integration.kafka.PlainEventPublisher;

public class Application {

    public static void main(String[] args) {
//        var eventPublisher = new PlainEventPublisher();
        var eventPublisher = new KafkaEventPublisher();

        var customerService = new CustomerService(eventPublisher);
        customerService.createCustomer(
            Customer.builder()
                .customerId(UUID.randomUUID())
                .email("my@email")
                .build()
        );
    }

}
