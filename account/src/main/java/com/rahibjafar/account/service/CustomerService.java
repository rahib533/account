package com.rahibjafar.account.service;


import com.rahibjafar.account.exception.CustomerNotFoundException;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id = " + id.toString()));
    }
}
