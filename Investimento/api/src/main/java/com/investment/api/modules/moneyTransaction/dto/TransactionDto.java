package com.investment.api.modules.moneyTransaction.dto;

public record TransactionDto(
    String nome,
    double valor,
    Long numero
) {}
