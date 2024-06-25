package com.investment.api.modules.moneyTransaction.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.api.modules.moneyTransaction.forms.TransactionForm;
import com.investment.api.modules.moneyTransaction.services.ContributeService;
import com.investment.api.modules.moneyTransaction.services.RedeemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
@Valid
public class MoneyTransactionController {
    
    private final RedeemService redeemService;
    private final ContributeService contributeService;

    public MoneyTransactionController(RedeemService redeemService, ContributeService contributeService) {
        this.redeemService = redeemService;
        this.contributeService = contributeService;
    }

    @PostMapping("/redeem")
    public ResponseEntity<Void> redeem(@Valid @RequestBody TransactionForm transactionForm){
        this.redeemService.redeem(transactionForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/contribute")
    public ResponseEntity<Void> financialSupport(@Valid @RequestBody TransactionForm transactionForm){
        this.contributeService.contribute(transactionForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
