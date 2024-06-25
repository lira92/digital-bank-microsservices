package com.investment.api.modules.moneyTransaction.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record TransactionForm(
    @NotNull(message = "Id do objetivo não pode estar vazio")
    @JsonProperty("goal_id")
    Long goalId,
    @NotNull(message = "Valor não pode estar vazio")
    float amount
) {}
