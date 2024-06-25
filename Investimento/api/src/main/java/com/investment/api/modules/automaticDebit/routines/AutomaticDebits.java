package com.investment.api.modules.automaticDebit.routines;

import com.investment.api.modules.automaticDebit.services.AutomaticDebitsService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutomaticDebits {
    
    private final AutomaticDebitsService automaticDebitService;

    public AutomaticDebits(AutomaticDebitsService automaticDebitService) {
        this.automaticDebitService = automaticDebitService;
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void automaticDebit() {
        this.automaticDebitService.automaticDebit();
    }
}
