package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.AccountDto;
import com.rahibjafar.account.dto.CreateAccountRequest;
import com.rahibjafar.account.exception.AccountNotFoundException;
import com.rahibjafar.account.mapper.AccountMapper;
import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setBalance(createAccountRequest.balance());

        Customer customer = customerService.getCustomerById(createAccountRequest.customerId());
        account.setCustomer(customer);

        Account createdAccount = accountRepository.save(account);

        return accountMapper.toDto(createdAccount);
    }

    public List<AccountDto> getAllAccounts() {
        return accountMapper.toDto(accountRepository.findAll());
    }

    public AccountDto getAccountById(UUID id) {
        Account foundedAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found with id = " + id.toString()));
        return accountMapper.toDto(foundedAccount);
    }
}
