package com.bank.transactionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private Long transactionId;

    private Long accountId;

    private String transactionType;

    private Double amount;

    private LocalDateTime transactionDate;

}
