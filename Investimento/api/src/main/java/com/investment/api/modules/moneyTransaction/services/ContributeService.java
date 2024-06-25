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
public class ContributeService {

    private final ApiService apiService;
    private final UpdateAmountService updateAmountService;
    private final GoalRepository goalRepository;

    private TransferType transferType;
    private ActionType actionType;

    public ContributeService(ApiService apiService, UpdateAmountService updateAmountService,
            GoalRepository goalRepository) {
        this.apiService = apiService;
        this.updateAmountService = updateAmountService;
        this.goalRepository = goalRepository;
        this.transferType = TransferType.DEDUCTION_INVEST;
        this.actionType = ActionType.MANUAL;
    }

    public void contribute(TransactionForm transactionForm, ActionType actionType) {
        Goal goal = this.goalRepository.findById(transactionForm.goalId()).orElseThrow();

        if (ActionType.AUTOMATIC.equals(actionType)) {
            this.transferType = TransferType.AUTOMATIC_DEDUCTION;
            this.actionType = actionType;
        }

        boolean transactionSuccess = false;

        try {
            transactionSuccess = this.tranferMoney(transactionForm.amount(), goal.getAccountNumber());
        } catch (ExternalApiException error) {
            if ((error.getResponseBody() != null)&&(error.getResponseBody().contains("Valor excede o saldo da conta! Email foi enviado ao dono."))) {
                throw new NoCashException();
            }

            throw error;
        }

        if (transactionSuccess) {
            this.updateAmountService.updateAmount(goal,
                    this.createTransactionHistory(goal, transactionForm.amount()));
        }
    }

    private TransactionHistory createTransactionHistory(Goal goal, float amount) {
        return new TransactionHistory(
                this.actionType,
                Action.CONTRIBUTE,
                amount,
                goal);
    }

    private boolean tranferMoney(float amount, Long accountNumber) {
        TransactionDto transactionDto = new TransactionDto(this.transferType.getValue(), amount,
                accountNumber);
        String url = UrlEnum.CONTA_CORRENTE.getValue() + "/debitar";

        ResponseDto responseDto = this.apiService.patch(url, transactionDto);

        if (!responseDto.statusCode().is2xxSuccessful()) {
            throw new ExternalApiException(responseDto.statusCode().value(),
                    "Erro ao transferir dinheiro - falha de comunicação com o serviço");
        }

        return true;
    }
}
