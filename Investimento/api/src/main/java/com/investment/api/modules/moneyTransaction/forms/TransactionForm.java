package com.investment.api.modules.moneyTransaction.forms;

import jakarta.validation.constraints.NotNull;

public record TransactionForm(
    @NotNull(message = "Id do objetivo não pode estar vazio")
    Long goalId,
    @NotNull(message = "Valor não pode estar vazio")
    float amount
) {}
