package com.investment.api.modules.accrual.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.investment.api.modules.goal.repositories.GoalRepository;
import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.moneyTransaction.entities.TransactionHistory;
import com.investment.api.modules.moneyTransaction.enums.ActionType;
import com.investment.api.modules.moneyTransaction.enums.Action;
import com.investment.api.modules.moneyTransaction.repositories.TransactionHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class ApplyAccrualService {

    private final GetRateService getRateService;
    private final GoalRepository goalRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public ApplyAccrualService(GetRateService getRateService, GoalRepository goalRepository,
            TransactionHistoryRepository transactionHistoryRepository) {
        this.getRateService = getRateService;
        this.goalRepository = goalRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional
    public void applyAccrual() {
        double dailyRate = this.getRateService.getRate();
        this.registerHistory(dailyRate);
        this.goalRepository.updateGoalsWithDailyRate(dailyRate);
    }

    @Transactional
    private void registerHistory(double dailyRate) {
        List<Goal> goals = this.goalRepository.findAll();

        for (Goal goal : goals) {
            double amount =  (goal.getCurrentValue() * (1 + dailyRate)) - goal.getCurrentValue();
            TransactionHistory transactionHistory = new TransactionHistory(
                    ActionType.DAILY_RATE,
                    Action.CONTRIBUTE,
                    (float) amount,
                    goal);

            this.transactionHistoryRepository.save(transactionHistory);
        }
    }
}