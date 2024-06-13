package com.biopark.notifications.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.biopark.notifications.api.services.EmailService;

import jakarta.mail.MessagingException;

@RestController
public class NotificationsController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/notifications/send")
    public void sendEmailNotification(@RequestBody String[] messageRecipients, String messageSubject, String messageBody) throws MessagingException {
        if (StringUtils.hasText(messageBody)) {
            emailService.handleSendEmailMessage(messageRecipients, messageSubject, messageBody);
        }
    }
}
