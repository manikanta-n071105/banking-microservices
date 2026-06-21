package com.bank.accountservice.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private Long customerId;
    private String name;
    private String email;
    private String phone;

}
