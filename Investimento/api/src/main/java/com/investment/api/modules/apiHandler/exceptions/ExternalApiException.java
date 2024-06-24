package com.investment.api.modules.apiHandler.exceptions;

public class ExternalApiException extends RuntimeException {
    
    private final int statusCode;
    private final String message;

    public ExternalApiException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
