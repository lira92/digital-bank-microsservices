package com.investment.api.modules.goal.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.investment.api.modules.goal.forms.GoalForm;
import com.investment.api.modules.moneyTransaction.entities.TransactionHistory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "goals")
@SQLDelete(sql = "UPDATE goals SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnTransformer(write = "LOWER(?)")
    @NotBlank(message = "Nome do objetivo não pode estar vazio")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Valor atual não pode estar vazio")
    @Column(nullable = false)
    private double currentValue = 0.0f;

    @NotNull(message = "Valor do objetivo não pode estar vazio")
    @Column(nullable = false)
    private Double targetValue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "YYYY-mm-dd")
    @NotNull(message = "Data limite não pode estar vazia")
    @Column(nullable = false)
    private LocalDate targetDate;

    @NotNull(message = "Id da conta não pode estar vazio")
    @Column(nullable = false)
    private Long accountId;

    @NotNull(message = "Email do usuário não pode estar vazio")
    @Column(nullable = false)
    @Setter
    private String userEmail;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeductionRegister> deductionRegisters;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionHistory> transactionHistory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    public Goal(String name, double targetValue, LocalDate targetDate, Long accountId) {
        this.name = name;
        this.currentValue = 0.0f;
        this.targetValue = targetValue;
        this.targetDate = targetDate;
        this.accountId = accountId;
        this.userEmail = "";
        this.deleted = false;
    }

    public Goal(Long id, String name, float currentValue, double targetValue, LocalDate targetDate, Long accountId,
            String userEmail, LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted) {
        this.id = id;
        this.name = name;
        this.currentValue = currentValue;
        this.targetValue = targetValue;
        this.targetDate = targetDate;
        this.accountId = accountId;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }

    public void addDeductionRegister(DeductionRegister deductionRegister) {
        this.deductionRegisters.add(deductionRegister);
    }

    public void addDeductionRegister(List<DeductionRegister> deductionRegisters) {
        this.deductionRegisters.addAll(deductionRegisters);
    }

    public void updateAmount(float amount) {
        if (canUpdateAmount(amount)) {

        }
        
        this.currentValue += amount;
    }

    public boolean canUpdateAmount(float amount) {
        return this.currentValue + amount < 0;
    }

    public static Goal fromForm(GoalForm form) {
        return new Goal(
            form.name(),
            form.targetValue(),
            form.targetDate(),
            form.accountId() 
        );
    }
}
