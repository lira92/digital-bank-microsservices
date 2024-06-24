package com.investment.api.modules.moneyTransaction.exceptions;

public class NoCashException extends RuntimeException{

    public NoCashException() {
        super("Não há dinheiro suficiente para realizar essa operação");
    }
}
