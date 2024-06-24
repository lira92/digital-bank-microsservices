package com.investment.api.modules.moneyTransaction.enums;

public enum TransferType {
    COMPESATE("Compensação"),
    DEDUCTION_INVEST("dedução - investimento"),
    CREDIT_INVEST("crédito - investimento");

    private final String value;

    TransferType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
