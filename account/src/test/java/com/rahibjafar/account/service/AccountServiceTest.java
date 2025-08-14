package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.CreateAccountRequest;
import com.rahibjafar.account.exception.AccountNotFoundException;
import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AccountService accountService;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = new Customer();
        // Lazım olduqda digər field-ləri də set edə bilərsiniz
        // məsələn: customer.setName("Rahib");
        // əsas odur ki, obyekt null olmasın
    }

    @Test
    void createAccount_shouldSaveAccountWithGivenBalanceAndCustomer() {
        // given
        BigDecimal initialBalance = new BigDecimal("150.75");
        CreateAccountRequest request = new CreateAccountRequest(initialBalance, customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        Account saved = new Account();
        // adətən JPA save-dən sonra id dolur
        // əlinizdə setId yoxdursa, bu hissəni keçin — əsas odur ki, eyni obyekt qaytarılsın
        when(accountRepository.save(any(Account.class))).thenReturn(saved);

        // when
        Account result = accountService.createAccount(request);

        // then
        verify(customerService, times(1)).getCustomerById(customerId);
        verify(accountRepository, times(1)).save(accountCaptor.capture());

        Account toSave = accountCaptor.getValue();
        assertNotNull(toSave, "Repository-yə göndərilən Account null olmamalıdır");
        assertEquals(initialBalance, toSave.getBalance(), "Balance düzgün set olunmalıdır");
        assertSame(customer, toSave.getCustomer(), "Customer düzgün set olunmalıdır");

        assertSame(saved, result, "Service save-dən qayıdan obyekti qaytarmalıdır");
    }

    @Test
    void getAllAccounts_shouldReturnListFromRepository() {
        // given
        Account a1 = new Account();
        Account a2 = new Account();
        List<Account> accounts = Arrays.asList(a1, a2);
        when(accountRepository.findAll()).thenReturn(accounts);

        // when
        List<Account> result = accountService.getAllAccounts();

        // then
        verify(accountRepository, times(1)).findAll();
        assertEquals(2, result.size(), "İki hesab qaytarmalıdır");
        assertTrue(result.contains(a1) && result.contains(a2));
    }

    @Test
    void getAccountById_whenFound_shouldReturnAccount() {
        // given
        UUID id = UUID.randomUUID();
        Account acc = new Account();
        when(accountRepository.findById(id)).thenReturn(Optional.of(acc));

        // when
        Account result = accountService.getAccountById(id);

        // then
        verify(accountRepository, times(1)).findById(id);
        assertSame(acc, result);
    }

    @Test
    void getAccountById_whenNotFound_shouldThrowAccountNotFoundException() {
        // given
        UUID id = UUID.randomUUID();
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        AccountNotFoundException ex = assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccountById(id)
        );

        assertTrue(
                ex.getMessage() != null && ex.getMessage().contains(id.toString()),
                "Exception mesajı id-ni ehtiva etməlidir"
        );
        verify(accountRepository, times(1)).findById(id);
    }
}
