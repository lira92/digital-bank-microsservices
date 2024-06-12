package com.investment.api.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE aluno SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    @NotBlank(message = "Nome do objetivo não pode estar vazio")
    private String name;
    @Column(name = "current_value", nullable = false)
    @Builder.Default
    private float currentValue = 0.0f;

    @Column(name = "target_value", nullable = false)
    @NotBlank(message = "Valor do objetivo não pode estar vazio")
    private double targetValue;
    @Column(name = "target_date", nullable = false)
    @NotBlank(message = "Data limite não pode estar vazia")
    private Date targetDate;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "Id do usuário não pode estar vazio")
    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    private boolean deleted = false;
}
