package com.investment.api.modules.goal.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.investment.api.exceptions.HttpException;
import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;
import com.investment.api.modules.apiHandler.services.ApiService;
import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.goal.forms.GoalForm;
import com.investment.api.modules.goal.repositories.GoalRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateGoalService {

    private final GoalRepository goalRepository;
    private final ApiService apiService;

    public CreateGoalService(GoalRepository goalRepository, ApiService apiService) {
        this.goalRepository = goalRepository;
        this.apiService = apiService;
    }

    @Transactional
    public Goal createGoal(GoalForm goalForm) {
        String email = this.findUserEmail(goalForm.accountNumber());
        Goal goal = Goal.fromForm(goalForm);
        goal.setUserEmail(email);
        goal.addDeductionRegister(
                CreateDeductionRegisterCollection.createDeductionRegisterCollection(goal, goalForm.deductionDate()));
        this.goalRepository.save(goal);
        return goal;
    }

    private String findUserEmail(Long accountNumber) {
        String url = UrlEnum.CONTA_CORRENTE.getValue() + "/numero/" + accountNumber;

        ResponseDto response = null;

        try {
            response = this.apiService.get(url);
        } catch (ExternalApiException error) {
            // Pequena gambiarra porque a API de conta retorna 400 para tudo que não está ok
            if (error.getMessage().contains("Conta não encontrada!")) {
                throw new HttpException(HttpStatus.NOT_FOUND, "Conta informada não existente");
            }

            throw error;
        }

        return response.body().get("email").toString();
    }
}
