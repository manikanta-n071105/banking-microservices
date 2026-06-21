package com.bank.transactionservice.dto;

import lombok.Data;

@Data
public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private Double Amount;

}
