package com.investment.api.modules.moneyTransaction.enums;

public enum TransferType {
    DEDUCTION_INVEST("aporte - investimento"),
    CREDIT_INVEST("resgate - investimento");

    private final String value;

    TransferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
