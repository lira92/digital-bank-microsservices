package com.investment.api.modules.moneyTransaction.services;

import org.springframework.stereotype.Service;

import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;
import com.investment.api.modules.apiHandler.services.ApiService;
import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.goal.repositories.GoalRepository;
import com.investment.api.modules.moneyTransaction.dto.TransactionDto;
import com.investment.api.modules.moneyTransaction.entities.TransactionHistory;
import com.investment.api.modules.moneyTransaction.enums.Action;
import com.investment.api.modules.moneyTransaction.enums.ActionType;
import com.investment.api.modules.moneyTransaction.enums.TransferType;
import com.investment.api.modules.moneyTransaction.exceptions.NoCashException;
import com.investment.api.modules.moneyTransaction.forms.TransactionForm;

@Service
public class RedeemService {

    private final ApiService apiService;
    private final UpdateAmountService updateAmountService;
    private final GoalRepository goalRepository;

    public RedeemService(ApiService apiService, UpdateAmountService updateAmountService,
            GoalRepository goalRepository) {
        this.apiService = apiService;
        this.updateAmountService = updateAmountService;
        this.goalRepository = goalRepository;
    }

    public void redeem(TransactionForm transactionForm) {
        Goal goal = this.goalRepository.findById(transactionForm.goalId()).orElseThrow();

        if (!goal.canUpdateAmount(transactionForm.amount())) {
            throw new NoCashException();
        }

        if (this.tranferMoney(transactionForm.amount(), goal.getAccountId())) {
            this.updateAmountService.updateAmount(goal, this.createTransactionHistory(goal, transactionForm.amount()));
        }
    }

    private TransactionHistory createTransactionHistory(Goal goal, float amount) {
        return new TransactionHistory(
                ActionType.MANUAL,
                Action.REDEEM,
                amount,
                goal);
    }

    private boolean tranferMoney(float amount, Long accountId) {
        TransactionDto transactionDto = new TransactionDto(TransferType.CREDIT_INVEST.getValue(), amount, accountId);
        String url = UrlEnum.CONTA_CORRENTE.getValue() + "/creditar";

        ResponseDto responseDto = this.apiService.post(url, transactionDto);

        if (!responseDto.statusCode().is2xxSuccessful()) {
            throw new ExternalApiException(responseDto.statusCode().value(), "Erro ao transferir dinheiro - falha de comunicação com o serviço");
        }

        return true;
    }
}
