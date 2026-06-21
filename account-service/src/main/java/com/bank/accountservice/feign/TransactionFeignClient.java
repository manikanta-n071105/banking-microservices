package com.bank.accountservice.feign;

import com.bank.accountservice.dto.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TRANSACTION-SERVICE")
public interface TransactionFeignClient {

    @PostMapping("/transactions")
    void createTransaction(
            @RequestBody TransactionRequest request);
}