package com.investment.api.modules.moneyTransaction.services;

import org.springframework.stereotype.Service;

import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.goal.repositories.GoalRepository;
import com.investment.api.modules.moneyTransaction.entities.TransactionHistory;
import com.investment.api.modules.moneyTransaction.repositories.TransactionHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class UpdateAmountService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final GoalRepository goalRepository;

    public UpdateAmountService(TransactionHistoryRepository transactionHistoryRepository,
            GoalRepository goalRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.goalRepository = goalRepository;
    }

    @Transactional
    public void updateAmount(Goal goal, TransactionHistory transactionHistory) {
        goal.updateAmount(transactionHistory.getAmount());
        this.goalRepository.save(goal);
        this.transactionHistoryRepository.save(transactionHistory);
    }
}
