package com.investment.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.investment.api.services.GoalService;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Autowired
    private GoalService goalService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateIncomeDiary() {
        goalService.calculateIncomeDiary();
    }
}
