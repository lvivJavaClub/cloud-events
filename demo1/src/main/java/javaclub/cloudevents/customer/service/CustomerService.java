package javaclub.cloudevents.customer.service;

import javaclub.cloudevents.customer.model.Customer;
import javaclub.cloudevents.customer.model.CustomerCreated;
import javaclub.cloudevents.integration.event.EventPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerService {

    private final EventPublisher eventPublisher;

    public Customer createCustomer(Customer source) {
        System.out.println("source = " + source);
        eventPublisher.publishEvent(
            CustomerCreated.builder().customer(source).build()
        );
        return source;
    }

}
