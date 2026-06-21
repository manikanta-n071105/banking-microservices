package com.bank.transactionservice.service;

import com.bank.transactionservice.dto.AccountDto;
import com.bank.transactionservice.dto.TransactionRequest;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.entity.TransactionType;
import com.bank.transactionservice.feign.AccountFeignClient;
import com.bank.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import com.bank.transactionservice.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountFeignClient accountFeignClient;

    public TransactionEntity create(TransactionRequest request) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccountId(request.getAccountId());
        transactionEntity.setAmount(request.getAmount());
        transactionEntity.setTransactionType(
                TransactionType.valueOf(request.getTransactionType().toUpperCase()));
        transactionEntity.setTransactionDate(LocalDateTime.now());

        return repository.save(transactionEntity);
    }


    public List<TransactionEntity> getAll() {
        return repository.findAll();
    }

    public List<TransactionEntity> getByAccount(Long accountId){
        return repository.findByAccountId(accountId);
    }

    public TransactionEntity deposit(Long accountId, Double amount) {

        accountFeignClient.deposit(accountId, amount);

        TransactionEntity transaction = TransactionEntity.builder()
                .accountId(accountId)
                .amount(amount)
                .transactionType(TransactionType.DEPOSIT)
                .transactionDate(LocalDateTime.now())
                .build();

        return repository.save(transaction);
    }

    public TransactionEntity withdraw(Long accountId, Double amount) {

        accountFeignClient.withdraw(accountId, amount);

        TransactionEntity transaction = TransactionEntity.builder()
                .accountId(accountId)
                .amount(amount)
                .transactionType(TransactionType.WITHDRAW)
                .transactionDate(LocalDateTime.now())
                .build();

        return repository.save(transaction);
    }

    public TransactionEntity transfer(
            TransferRequest request) {

        accountFeignClient.withdraw(
                request.getFromAccountId(),
                request.getAmount());

        accountFeignClient.deposit(
                request.getToAccountId(),
                request.getAmount());

        TransactionEntity transaction =
                TransactionEntity.builder()
                        .accountId(request.getFromAccountId())
                        .accountId(
                                request.getToAccountId())
                        .amount(request.getAmount())
                        .transactionType(
                                TransactionType.TRANSFER)
                        .transactionDate(
                                LocalDateTime.now())
                        .build();

        return repository.save(transaction);
    }}
