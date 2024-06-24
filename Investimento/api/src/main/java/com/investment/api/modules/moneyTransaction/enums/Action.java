package com.investment.api.modules.moneyTransaction.enums;

public enum Action {
    CONTRIBUTE("crédito"),
    REDEEM("débito");   

    private final String value;

    Action(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
