package com.investment.api.modules.goal.services;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
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
        String email = this.findUserEmail(goalForm.accountId());
        Goal goal = Goal.fromForm(goalForm);
        goal.setUserEmail(email);
        goal.addDeductionRegister(
                CreateDeductionRegisterCollection.createDeductionRegisterCollection(goal, goalForm.deductionDate()));
        this.goalRepository.save(goal);
        return goal;
    }

    private String findUserEmail(Long userId) {
        String url = UrlEnum.CONTA_CORRENTE.getValue() + "/" + userId;
        ResponseDto response = this.apiService.get(url);

        if (response.statusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Usuario não encontrado");
        }

        return response.body().get("email").toString();
    }
}
