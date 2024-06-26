package com.investment.api.modules.mailer.dto;

import java.util.List;

public record MailDto(
    List<String> messageRecipients,
    String messageSubject,
    String messageBody
) {}
