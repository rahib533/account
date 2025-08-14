package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.CreateTransactionRequest;
import com.rahibjafar.account.dto.UpdateTransactionRequest;
import com.rahibjafar.account.exception.TransactionNotFoundException;
import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.ModelStatus;
import com.rahibjafar.account.model.Transaction;
import com.rahibjafar.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction createTransaction(final CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(createTransactionRequest.transactionType());
        transaction.setAmount(createTransactionRequest.amount());
        transaction.setDescription(createTransactionRequest.description());

        Account account = accountService.getAccountById(createTransactionRequest.accountId());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(final UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id = " + id.toString()));
    }

    @Transactional
    public Transaction updateTransaction(final UpdateTransactionRequest updateTransactionRequest) {
        Transaction transaction = getTransactionById(updateTransactionRequest.id());
        Account account = accountService.getAccountById(updateTransactionRequest.accountId());

        transaction.setAccount(account);
        transaction.setAmount(updateTransactionRequest.amount());
        transaction.setDescription(updateTransactionRequest.description());
        transaction.setTransactionType(updateTransactionRequest.transactionType());

        return transaction;
    }

    @Transactional
    public Transaction deleteTransaction(final UUID id) {
        Transaction transactionToDelete = getTransactionById(id);
        transactionToDelete.setStatus(ModelStatus.INACTIVE);
        return transactionToDelete;
    }
}
