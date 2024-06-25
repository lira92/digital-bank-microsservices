package com.investment.api.modules.goal.forms;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GoalForm(
    @NotNull(message = "account_number não pode ser nulo")
    @JsonProperty("account_number")
    Long accountNumber,

    @NotBlank(message = "name não pode estar vazio")
    String name,
   
    @NotNull(message = "target_value não pode estar vazio")
    @JsonProperty("target_value")
    Double targetValue,
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "target_date não pode estar vazia")
    @JsonProperty("target_date")
    LocalDate targetDate,
    
    @NotNull(message = "deduction_date não pode estar vazia")
    @JsonProperty("deduction_day")
    @Max(value = 31, message = "O dia de dedução deve ser menor ou igual a 31")
    @Min(value = 1, message = "O dia de dedução deve ser maior ou igual a 1")
    int deductionDate
) {}
