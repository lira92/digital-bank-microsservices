package com.investment.api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investment.api.repositories.GoalRepository;
import com.investment.api.entities.Goal;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public void calculateIncomeDiary() {
        List<Goal> goals = goalRepository.findAll();
        for (Goal goal : goals) {
         
        }
    }
}
