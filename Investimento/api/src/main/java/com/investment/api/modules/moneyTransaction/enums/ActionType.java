package com.investment.api.modules.moneyTransaction.enums;

public enum ActionType {
    AUTOMATIC("Automático"),
    MANUAL("Manual"),
    DAILY_RATE("Rendimento diario");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
