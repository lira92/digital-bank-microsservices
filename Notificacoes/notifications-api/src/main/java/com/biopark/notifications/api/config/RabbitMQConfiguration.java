package com.biopark.notifications.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE_NAME;

    @Value("${spring.rabbitmq.queue}")
    private String QUEUE_NAME;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String ROUTING_KEY;

    @Bean
    public TopicExchange notificationsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(notificationsQueue())
                .to(notificationsExchange())
                .with(ROUTING_KEY);
    }
}
