package com.bank.accountservice.dto;

import lombok.Data;

@Data
public class AccountRequest {

    private Long customerId;
    private String accountType;
    private Double initialBalance;

}
