package com.investment.api.modules.moneyTransaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment.api.modules.moneyTransaction.entities.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{
    
}
