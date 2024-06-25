package com.investment.api.modules.accrual.routines;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.investment.api.modules.accrual.services.ApplyAccrualService;

@Service
public class ApplyAccrual {

    private final ApplyAccrualService applyAccrualService;

    public ApplyAccrual(ApplyAccrualService applyAccrualService) {
        this.applyAccrualService = applyAccrualService;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void applyAccrual() {
        this.applyAccrualService.applyAccrual();   
    }
}