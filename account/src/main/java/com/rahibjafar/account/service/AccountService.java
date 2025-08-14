package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.CreateAccountRequest;
import com.rahibjafar.account.dto.UpdateAccountRequest;
import com.rahibjafar.account.exception.AccountNotFoundException;
import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.model.ModelStatus;
import com.rahibjafar.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    @Transactional
    public Account createAccount(final CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setBalance(createAccountRequest.balance());

        Customer customer = customerService.getCustomerById(createAccountRequest.customerId());
        account.setCustomer(customer);

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(final UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found with id = " + id.toString()));
    }

    @Transactional
    public Account updateAccount(final UpdateAccountRequest updateAccountRequest) {
        Account account = getAccountById(updateAccountRequest.id());
        Customer customer = customerService.getCustomerById(updateAccountRequest.customerId());

        account.setBalance(updateAccountRequest.balance());
        account.setCustomer(customer);

        return account;
    }

    @Transactional
    public Account deleteAccount(final UUID id) {
        Account accountToDelete = getAccountById(id);
        accountToDelete.setStatus(ModelStatus.INACTIVE);
        return accountToDelete;
    }
}
