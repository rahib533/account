package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.UpdateCustomerRequest;
import com.rahibjafar.account.exception.CustomerNotFoundException;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.model.ModelStatus;
import com.rahibjafar.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(final UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id = " + id.toString()));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer createCustomer(final Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(final UpdateCustomerRequest updateCustomerRequest) {
        Customer customerToUpdate = getCustomerById(updateCustomerRequest.id());
        customerToUpdate.setCif(updateCustomerRequest.cif());
        customerToUpdate.setFirstName(updateCustomerRequest.firstName());
        customerToUpdate.setLastName(updateCustomerRequest.lastName());

        return customerToUpdate;
    }

    @Transactional
    public Customer deleteCustomer(final UUID id) {
        Customer customerToDelete = getCustomerById(id);
        customerToDelete.setStatus(ModelStatus.INACTIVE);
        return customerToDelete;
    }
}
