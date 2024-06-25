package com.investment.api.modules.apiHandler.enums;

public enum UrlEnum {
    CONTA_CORRENTE("http://contacorrente:3003/api/conta"),
    EMAIL("http://notifications-api:3002/notifications"),
    BACK_OFFICE("http://backoffice-api:3007/configuracao/investimento");

    private final String value;

    UrlEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
