package com.investment.api.modules.automaticDebit.services;

import jakarta.transaction.Transactional;

import com.investment.api.modules.goal.repositories.DeductionRegisterRepository;
import com.investment.api.modules.goal.entities.DeductionRegister;
import com.investment.api.modules.goal.enums.DeductionStatus;
import com.investment.api.modules.moneyTransaction.services.ContributeService;
import com.investment.api.modules.moneyTransaction.forms.TransactionForm;
import com.investment.api.modules.moneyTransaction.enums.ActionType;
import com.investment.api.modules.moneyTransaction.exceptions.NoCashException;
import com.investment.api.modules.accrual.services.GetRateService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AutomaticDebitsService {

    private final DeductionRegisterRepository deductionRegisterRepository;
    private final ContributeService contributeService;
    private final GetRateService getRateService;

    public AutomaticDebitsService(DeductionRegisterRepository deductionRegisterRepository,
            ContributeService contributeService, GetRateService getRateService) {
        this.deductionRegisterRepository = deductionRegisterRepository;
        this.contributeService = contributeService;
        this.getRateService = getRateService;
    }

    @Transactional
    public void automaticDebit() {
        List<DeductionRegister> deductionRegisters = this.deductionRegisterRepository
                .findAllByDeductionDate(LocalDate.now());

        for (DeductionRegister deductionRegister : deductionRegisters) {
            double amount = this.calculateAmount(deductionRegister);
            this.debit(deductionRegister, amount);
        }
    }

    private double calculateAmount(DeductionRegister deductionRegister) {
        double dailyRate = this.getRateService.getRate();
        double monthlyRate = Math.pow(1.0 + dailyRate, 30.0) -1 ;
        int monthDiff = Period.between(LocalDate.now(), deductionRegister.getGoal().getTargetDate()).getMonths();
        double necessaryAmount = deductionRegister.getGoal().getTargetValue() - deductionRegister.getGoal().getCurrentValue();

        return necessaryAmount * monthlyRate / Math.pow((1.0 + monthlyRate), monthDiff);
    }

    private void debit(DeductionRegister deductionRegister, double amount) {
        TransactionForm transactionForm = new TransactionForm(deductionRegister.getGoal().getId(), (float) amount);

        try {
            this.contributeService.contribute(transactionForm, ActionType.AUTOMATIC);
            deductionRegister.setStatus(DeductionStatus.COMPLETED);
            this.deductionRegisterRepository.save(deductionRegister);
        } catch (NoCashException error) {
            deductionRegister.setStatus(DeductionStatus.FAILED);
            this.deductionRegisterRepository.save(deductionRegister);
            // send mail
        } catch (Exception error) {
            deductionRegister.setStatus(DeductionStatus.FAILED);
            this.deductionRegisterRepository.save(deductionRegister);
        }
    }
}
