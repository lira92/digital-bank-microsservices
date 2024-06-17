package com.biopark.notifications.api.services;

import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final String from;

    @Autowired
    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void handleSendEmailMessage(List<String> messageRecipients, String messageSubject, String messageBody) {
        sendEmail(messageRecipients, messageSubject, messageBody);
    }

    private InternetAddress[] getInternetAddressesFromRecipients(List<String> messageRecipients) {
        return messageRecipients
                .stream()
                .map(address -> {
                    try {
                        return new InternetAddress(address);
                    } catch (AddressException e) {
                        LOGGER.error("Failed to create InternetAddress", e);
                        throw new RuntimeException(e);
                    }
                })
                .toArray(InternetAddress[]::new);
    }

    private void prepareEmailMessage(
            MimeMessage message,
            InternetAddress[] addressesFromRecipients,
            String messageSubject,
            String messageBody)
            throws MessagingException {
        LOGGER.info("Preparing email message");
        message.setFrom(new InternetAddress(from));
        message.setRecipients(RecipientType.TO, addressesFromRecipients);
        message.setSubject(messageSubject);
        message.setContent(messageBody, "text/html; charset=utf-8");
        LOGGER.info("Email message prepared");
    }

    private void sendEmail(List<String> messageRecipients, String messageSubject, String messageBody) {
        MimeMessage message = mailSender.createMimeMessage();
        InternetAddress[] addressesFromRecipients = getInternetAddressesFromRecipients(messageRecipients);
        try {
            prepareEmailMessage(message, addressesFromRecipients, messageSubject, messageBody);
        } catch (MessagingException e) {
            LOGGER.error("Failed to prepare email message", e);
            throw new RuntimeException(e);
        }
        mailSender.send(message);
    }
}
