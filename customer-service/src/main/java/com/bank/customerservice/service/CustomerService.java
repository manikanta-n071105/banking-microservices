package com.bank.customerservice.service;

import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getALl() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
