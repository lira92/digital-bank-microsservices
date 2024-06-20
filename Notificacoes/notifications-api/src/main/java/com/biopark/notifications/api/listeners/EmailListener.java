package com.biopark.notifications.api.listeners;

import com.biopark.notifications.api.dtos.EmailRequest;
import com.biopark.notifications.api.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailListener.class);
    private final EmailService emailService;

    @Autowired
    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = {"send_email_notification"})
    public void receiveNotificationMessage(
        @Payload Message emailRequest
    ) throws MessagingException, JsonProcessingException {
        LOGGER.info("Received email request: {}", new String(emailRequest.getBody()));
        String emailRequestAsString = new String(emailRequest.getBody());
        EmailRequest emailRequestObject = new ObjectMapper().readValue(emailRequestAsString, EmailRequest.class);
        LOGGER.info("Email request object: {}", emailRequestObject);
        emailService.handleSendEmailMessage(
            emailRequestObject.getMessageRecipients(),
            emailRequestObject.getMessageSubject(),
            emailRequestObject.getMessageBody()
        );
        LOGGER.info("Email sent successfully!");
    }
}
