package com.bank.customerservice.controller;

import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return service.getALl();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id){
        return service.getCustomerById(id);
    }

    @GetMapping("/test")
    public String test() {
        return "TEST WORKING";
    }
}
