package com.bank.accountservice.feign;

import com.bank.accountservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerFeignClient {

    @GetMapping("/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);

}
