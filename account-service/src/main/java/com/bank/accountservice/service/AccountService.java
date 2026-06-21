package com.bank.accountservice.service;

import com.bank.accountservice.dto.AccountRequest;
import com.bank.accountservice.dto.AccountResponse;
import com.bank.accountservice.dto.CustomerDTO;
import com.bank.accountservice.dto.TransactionRequest;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.feign.CustomerFeignClient;
import com.bank.accountservice.feign.TransactionFeignClient;
import com.bank.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final CustomerFeignClient customerClient;
    private final TransactionFeignClient transactionClient;

    public Account createAccount(AccountRequest request){
        CustomerDTO customerDTO = customerClient.getCustomerById(request.getCustomerId());

        if(customerDTO == null){
            throw new RuntimeException("Customer not found");
        }

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .accountType(request.getAccountType())
                .balance(request.getInitialBalance())
                .customerId(request.getCustomerId())
                .build();
        return repository.save(account);
    }

    public AccountResponse getAccount(Long accountId) {
        Account account = repository.findById(accountId)
                .orElseThrow(()->
                        new RuntimeException("Account Not Found"));

        CustomerDTO customer = customerClient.getCustomerById(account.getCustomerId());

        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .customer(customer)
                .build();
    }

    public Account deposit(Long accountId, Double amount) {

        Account account = repository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        Account saved = repository.save(account);

        transactionClient.createTransaction(
                new TransactionRequest(
                        accountId,
                        "DEPOSIT",
                        amount
                )
        );

        return saved;
    }
    public Account withdraw(Long accountId, Double amount) {

        Account account = repository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Account saved = repository.save(account);

        transactionClient.createTransaction(
                new TransactionRequest(
                        accountId,
                        "WITHDRAW",
                        amount
                )
        );

        return saved;
    }
}
