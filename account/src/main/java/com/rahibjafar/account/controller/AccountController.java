package com.rahibjafar.account.controller;

import com.rahibjafar.account.dto.AccountDto;
import com.rahibjafar.account.dto.CreateAccountRequest;
import com.rahibjafar.account.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/getAll")
    public List<AccountDto> getAll() {
        return accountService.getAllAccounts();
    }

    @GetMapping("get/{id}")
    public AccountDto get(@PathVariable UUID id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/create")
    public AccountDto create(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }
}
