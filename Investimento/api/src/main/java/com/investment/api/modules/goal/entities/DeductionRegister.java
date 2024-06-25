package com.investment.api.modules.goal.entities;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.investment.api.modules.goal.enums.DeductionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "deduction_registers")
@SQLDelete(sql = "UPDATE deduction_registers SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
public class DeductionRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "YYYY-mm-dd")
    @NotNull(message = "Data de coleta não pode estar vazia")
    @Column(nullable = false)
    private LocalDate deductionDate;

    @NotNull(message = "Status não pode estar vazio")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private DeductionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(nullable = false)
    private boolean deleted = false;

    public DeductionRegister(Long id, LocalDate deductionDate, DeductionStatus status, Goal goal, boolean deleted) {
        this.id = id;
        this.deductionDate = deductionDate;
        this.status = status;
        this.goal = goal;
        this.deleted = deleted;
    }

    public DeductionRegister(LocalDate deductionDate, Goal goal) {
        this.deductionDate = deductionDate;
        this.status = DeductionStatus.SCHEDULED;
        this.goal = goal;
    }

    public DeductionRegister() {
        
    }
}
