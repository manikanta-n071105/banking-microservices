package com.bank.accountservice.controller;

import com.bank.accountservice.dto.AccountRequest;
import com.bank.accountservice.dto.AccountResponse;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public Account createAccount(
            @RequestBody AccountRequest request) {

        return service.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(
            @PathVariable Long id) {

        return service.getAccount(id);
    }

    @PutMapping("/{id}/deposit")
    public Account deposit(
            @PathVariable Long id,
            @RequestParam Double amount) {

        return service.deposit(id, amount);
    }

    @PutMapping("/{id}/withdraw")
    public Account withdraw(
            @PathVariable Long id,
            @RequestParam Double amount) {

        return service.withdraw(id, amount);
    }


}
