package com.investment.api.modules.moneyTransaction.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.moneyTransaction.enums.Action;
import com.investment.api.modules.moneyTransaction.enums.ActionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "transaction_history")
@SQLDelete(sql = "UPDATE deduction_registers SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tipo de ação não pode estar vazio")
    @Column(nullable = false)
    private ActionType actionType;

    @NotNull(message = "Ação não pode estar vazia")
    @Column(nullable = false)
    private Action action;

    @NotNull(message = "Montante não pode estar vazio")
    @Column(nullable = false)
    private float amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    public TransactionHistory(ActionType actionType, Action action, float amount, Goal goal) {
        this.actionType = actionType;
        this.action = action;
        this.amount = amount;
        this.goal = goal;
    }

    public TransactionHistory(Long id, ActionType actionType, Action action, float amount, Goal goal) {
        this.id = id;
        this.actionType = actionType;
        this.action = action;
        this.amount = amount;
        this.goal = goal;
    }
}
