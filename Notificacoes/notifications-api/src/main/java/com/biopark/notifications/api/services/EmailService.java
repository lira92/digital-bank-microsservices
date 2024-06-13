package com.biopark.notifications.api.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String from;

    @Autowired
    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void handleSendEmailMessage(String[] messageRecipients, String messageSubject, String messageBody) throws MessagingException {
        if (isHtml(messageBody)) {
            sendMimeEmail(messageRecipients, messageSubject, messageBody);
        } else {
            sendSimpleEmail(messageRecipients, messageSubject, messageBody);
        }
    }

    public void sendSimpleEmail(String[] messageRecipients, String messageSubject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(messageRecipients);
        message.setSubject(messageSubject);
        message.setText(messageBody);

        mailSender.send(message);
    }

    public void sendMimeEmail(String[] messageRecipients, String messageSubject, String messageBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress(from));

        InternetAddress[] recipients = Arrays.stream(messageRecipients)
                .map(t -> {
                    try {
                        return new InternetAddress(t);
                    } catch (AddressException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .toArray(InternetAddress[]::new);

        message.setRecipients(MimeMessage.RecipientType.TO, recipients);
        message.setSubject(messageSubject);
        message.setContent(messageBody, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public boolean isHtml(String messageBody) {
        String trimmedBody = messageBody.trim();
        return trimmedBody.startsWith("<") && trimmedBody.endsWith(">") &&
               (trimmedBody.contains("<html>") || trimmedBody.contains("<body>") ||
                trimmedBody.contains("<p>") || trimmedBody.contains("<div>"));
    }
}
