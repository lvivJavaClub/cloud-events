package javaclub.cloudevents.integration.event;

public interface EventPublisher {

    <T> void publishEvent(T event);

}
