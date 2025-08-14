package com.rahibjafar.account.controller;

import com.rahibjafar.account.dto.*;
import com.rahibjafar.account.mapper.TransactionMapper;
import com.rahibjafar.account.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(final TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/getAll")
    public List<TransactionDto> getAll() {
        return transactionMapper.toDto(transactionService.getAllTransactions());
    }

    @GetMapping("get/{id}")
    public TransactionDto get(@PathVariable UUID id) {
        return transactionMapper.toDto(transactionService.getTransactionById(id));
    }

    @PostMapping("/create")
    public TransactionDto create(@RequestBody CreateTransactionRequest createTransactionRequest) {
        return transactionMapper.toDto(transactionService.createTransaction(createTransactionRequest));
    }

    @PutMapping("/update")
    public TransactionDto update(@RequestBody UpdateTransactionRequest updateTransactionRequest) {
        return transactionMapper.toDto(transactionService.updateTransaction(updateTransactionRequest));
    }

    @DeleteMapping("/delete/{id}")
    public TransactionDto delete(@PathVariable UUID id) {
        return transactionMapper.toDto(transactionService.deleteTransaction(id));
    }
}
