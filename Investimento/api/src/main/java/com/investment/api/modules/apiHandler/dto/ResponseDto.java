package com.investment.api.modules.apiHandler.dto;

import java.util.Map;

import org.springframework.http.HttpStatusCode;

public record ResponseDto(
    HttpStatusCode statusCode,
    Map<String, Object> body
) {}
