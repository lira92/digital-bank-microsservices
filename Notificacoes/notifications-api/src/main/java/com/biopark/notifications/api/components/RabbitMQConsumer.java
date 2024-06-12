package com.biopark.notifications.api.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
