package com.investment.api.modules.mailer.dto;

import java.util.List;

public record MailDto(
    List<String> to,
    String subject,
    String body
) {}
