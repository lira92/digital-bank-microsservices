package com.investment.api.modules.apiHandler.enums;

public enum UrlEnum {
    CONTA_CORRENTE("http://contacorrente:3003/api/conta"),
    EMAIL(""),
    BACK_OFFICE("http://api:3007/configuracao/investimento");

    private final String value;

    UrlEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
