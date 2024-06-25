package com.investment.api.modules.goal.enums;

public enum DeductionStatus {
    SCHEDULED("SCHEDULED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private final String value;
    
    DeductionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
