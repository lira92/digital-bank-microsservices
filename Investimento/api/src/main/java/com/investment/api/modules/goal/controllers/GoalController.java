package com.investment.api.modules.goal.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.api.modules.goal.entities.Goal;
import com.investment.api.modules.goal.forms.GoalForm;
import com.investment.api.modules.goal.services.CreateGoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
@Valid
public class GoalController {

    private final CreateGoalService createGoalService;
    
    public GoalController(CreateGoalService createGoalService) {
        this.createGoalService = createGoalService;
    }

    @PostMapping
    public ResponseEntity<Long> createGoal(@Validated @RequestBody GoalForm goalForm) {
        Goal goal = this.createGoalService.createGoal(goalForm);
        return ResponseEntity.ok(goal.getId());
    }
}
