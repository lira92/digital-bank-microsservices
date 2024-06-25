package com.investment.api.modules.apiHandler.exceptions;

public class ExternalApiException extends RuntimeException {
    
    private final int statusCode;
    private final String message;
    private final String responseBody;

    public ExternalApiException(int statusCode, String message, String responseBody) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseBody = responseBody;
    }

    public ExternalApiException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseBody = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
