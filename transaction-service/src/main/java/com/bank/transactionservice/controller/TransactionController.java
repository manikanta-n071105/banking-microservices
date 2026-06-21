package com.bank.transactionservice.controller;

import com.bank.transactionservice.dto.AccountDto;
import com.bank.transactionservice.dto.TransactionRequest;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.entity.TransactionEntity;
import com.bank.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public TransactionEntity create(
            @Valid @RequestBody TransactionRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TransactionEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionEntity> getByAccount(@PathVariable Long accountId) {
        return service.getByAccount(accountId);
    }

    @PostMapping("/deposit")
    public TransactionEntity deposit(
            @RequestParam Long accountId,
            @RequestParam Double amount) {

        return service.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public TransactionEntity withdraw(
            @RequestParam Long accountId,
            @RequestParam Double amount) {

        return service.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public String transfer(
            @RequestBody TransferRequest request){

        service.transfer(request);

        return "Transfer Successful";
    }


}
