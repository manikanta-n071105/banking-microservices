package com.bank.transactionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    @Positive
    private Double amount;

    private String transactionType;

}
