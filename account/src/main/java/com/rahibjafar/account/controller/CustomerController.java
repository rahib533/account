package com.rahibjafar.account.controller;

import com.rahibjafar.account.dto.*;
import com.rahibjafar.account.mapper.CustomerMapper;
import com.rahibjafar.account.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/getAll")
    public List<CustomerDto> getAll() {
        return customerMapper.toDto(customerService.getAllCustomers());
    }

    @GetMapping("get/{id}")
    public CustomerDto get(@PathVariable UUID id) {
        return customerMapper.toDto(customerService.getCustomerById(id));
    }

    @PostMapping("/create")
    public CustomerDto create(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerMapper.toDto(customerService.createCustomer(customerMapper.toEntity(createCustomerRequest)));
    }

    @PutMapping("/update")
    public CustomerDto update(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerMapper.toDto(customerService.updateCustomer(updateCustomerRequest));
    }

    @DeleteMapping("/delete/{id}")
    public CustomerDto delete(@PathVariable UUID id) {
        return customerMapper.toDto(customerService.deleteCustomer(id));
    }
}
