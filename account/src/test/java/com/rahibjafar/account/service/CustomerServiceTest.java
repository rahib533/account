package com.rahibjafar.account.service;

import com.rahibjafar.account.exception.CustomerNotFoundException;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("getCustomerById: mövcud müştəri qaytarılmalıdır")
    void getCustomerById_found_returnsCustomer() {
        // given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // when
        Customer result = customerService.getCustomerById(id);

        // then
        assertNotNull(result);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).findById(id);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("getCustomerById: tapılmadıqda CustomerNotFoundException atılmalıdır")
    void getCustomerById_notFound_throws() {
        // given
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        CustomerNotFoundException ex = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomerById(id)
        );
        assertTrue(ex.getMessage().contains(id.toString()));
        verify(customerRepository, times(1)).findById(id);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("getAllCustomers: repository-dən siyahı qaytarılmalıdır")
    void getAllCustomers_returnsList() {
        // given
        Customer c1 = new Customer();
        Customer c2 = new Customer();
        when(customerRepository.findAll()).thenReturn(List.of(c1, c2));

        // when
        List<Customer> result = customerService.getAllCustomers();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(c1, c2)));
        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("createCustomer: repository.save çağrılmalı və qaytarılan obyekt dönməlidir")
    void createCustomer_savesAndReturns() {
        // given
        Customer toCreate = new Customer();
        Customer saved = new Customer();
        when(customerRepository.save(toCreate)).thenReturn(saved);

        // when
        Customer result = customerService.createCustomer(toCreate);

        // then
        assertNotNull(result);
        assertEquals(saved, result);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(captor.capture());
        assertEquals(toCreate, captor.getValue());
        verifyNoMoreInteractions(customerRepository);
    }
}
