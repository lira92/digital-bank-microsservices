package com.biopark.notifications.api.controllers;

import com.biopark.notifications.api.dtos.EmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE_NAME;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String ROUTING_KEY;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public NotificationsController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/notifications/send")
    public ResponseEntity<String> sendEmailNotification(
            @Valid @RequestBody EmailRequest request
    ) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, objectMapper.writeValueAsString(request));
        return ResponseEntity.ok("Email request queued successfully");
    }
}
