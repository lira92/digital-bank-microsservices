package com.investment.api.modules.goal.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment.api.modules.goal.entities.DeductionRegister;

public interface DeductionRegisterRepository extends JpaRepository<DeductionRegister, Long> {
 
    List<DeductionRegister> findAllByDeductionDate(LocalDate deductionDate);
}
