package com.bank.accountservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    private Long accountId;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private CustomerDTO customer;

}
