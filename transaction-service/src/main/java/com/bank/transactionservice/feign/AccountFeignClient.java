package com.bank.transactionservice.feign;

import com.bank.transactionservice.dto.AccountDto;
import com.bank.transactionservice.dto.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface     AccountFeignClient {

    @PutMapping("/accounts/{id}/deposit")
    AccountDto deposit(
            @PathVariable("id") Long id,
            @RequestParam Double amount);

    @PutMapping("/accounts/{id}/withdraw")
    AccountDto withdraw(
            @PathVariable("id") Long id,
            @RequestParam Double amount);

    @PostMapping("/transaction")
    void createTransaction(
            @RequestBody TransactionRequest request);

}
