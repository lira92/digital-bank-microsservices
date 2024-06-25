package com.biopark.notifications.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class EmailRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Recipients cannot be empty.")
    @Email(message = "Invalid email address.")
    private List<String> messageRecipients;

    @NotEmpty(message = "Subject cannot be empty.")
    @Size(min = 5, max = 100, message = "Subject must be between 5 and 100 characters.")
    private String messageSubject;

    @NotEmpty(message = "Body cannot be empty.")
    private String messageBody;

    public EmailRequest() {
        // Default constructor
    }

    public EmailRequest(List<String> messageRecipients, String messageSubject, String messageBody) {
        this.messageRecipients = messageRecipients;
        this.messageSubject = messageSubject;
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "messageRecipients=" + messageRecipients +
                ", messageSubject='" + messageSubject + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }

    // Getters and Setters
    public List<String> getMessageRecipients() {
        return messageRecipients;
    }

    public void setMessageRecipients(List<String> messageRecipients) {
        this.messageRecipients = messageRecipients;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
