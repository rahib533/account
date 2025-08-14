package com.rahibjafar.account.controller;

import com.rahibjafar.account.dto.AccountDto;
import com.rahibjafar.account.dto.CreateAccountRequest;
import com.rahibjafar.account.dto.UpdateAccountRequest;
import com.rahibjafar.account.mapper.AccountMapper;
import com.rahibjafar.account.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/getAll")
    public List<AccountDto> getAll() {
        return accountMapper.toDto(accountService.getAllAccounts());
    }

    @GetMapping("get/{id}")
    public AccountDto get(@PathVariable UUID id) {
        return accountMapper.toDto(accountService.getAccountById(id));
    }

    @PostMapping("/create")
    public AccountDto create(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountMapper.toDto(accountService.createAccount(createAccountRequest));
    }

    @PutMapping("/update")
    public AccountDto update(@RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountMapper.toDto(accountService.updateAccount(updateAccountRequest));
    }

    @DeleteMapping("/delete/{id}")
    public AccountDto delete(@PathVariable UUID id) {
        return accountMapper.toDto(accountService.deleteAccount(id));
    }
}
