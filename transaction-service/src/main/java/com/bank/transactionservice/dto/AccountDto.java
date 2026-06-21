package com.bank.transactionservice.dto;

import lombok.Data;

public class AccountDto {

    @Data
    public class AccountDTO {

        private Long accountId;

        private String accountNumber;

        private String accountType;

        private Double balance;

        private Long customerId;
    }

}
