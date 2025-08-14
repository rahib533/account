package com.rahibjafar.account.service;

import com.rahibjafar.account.dto.CreateTransactionRequest;
import com.rahibjafar.account.dto.UpdateTransactionRequest;
import com.rahibjafar.account.exception.TransactionNotFoundException;
import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.ModelStatus;
import com.rahibjafar.account.model.Transaction;
import com.rahibjafar.account.model.TransactionType;
import com.rahibjafar.account.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    private UUID accountId;
    private UUID transactionId;

    private Account existingAccount;
    private Account anotherAccount;

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        transactionId = UUID.randomUUID();

        existingAccount = new Account();

        anotherAccount = new Account();
    }

    private Transaction buildExistingTransaction() {
        Transaction t = new Transaction();

        t.setAccount(existingAccount);
        t.setAmount(new BigDecimal("100.00"));
        t.setDescription("old desc");
        t.setTransactionType(TransactionType.DEPOSIT);
        t.setStatus(ModelStatus.ACTIVE);
        return t;
    }

    @Test
    @DisplayName("Yeni transaction yaradılmalı və repository.save() çağırılmalıdır")
    void createTransaction_shouldSaveAndReturnTransaction() {
        // given
        var request = new CreateTransactionRequest(
                TransactionType.WITHDRAWAL,
                new BigDecimal("250.50"),
                "cash withdraw",
                accountId
        );

        when(accountService.getAccountById(accountId)).thenReturn(existingAccount);

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> {
            Transaction toSave = inv.getArgument(0);
            toSave.setStatus(ModelStatus.ACTIVE);
            return toSave;
        });

        // when
        Transaction created = transactionService.createTransaction(request);

        // then
        verify(accountService).getAccountById(accountId);
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction saved = transactionCaptor.getValue();
        assertThat(saved.getAccount()).isEqualTo(existingAccount);
        assertThat(saved.getAmount()).isEqualByComparingTo("250.50");
        assertThat(saved.getDescription()).isEqualTo("cash withdraw");
        assertThat(saved.getTransactionType()).isEqualTo(TransactionType.WITHDRAWAL);

        assertThat(created).isNotNull();
        assertThat(created.getStatus()).isEqualTo(ModelStatus.ACTIVE);
    }

    @Test
    @DisplayName("Bütün transaction-lar geri qaytarılmalıdır")
    void getAllTransactions_shouldReturnList() {
        // given
        Transaction t1 = buildExistingTransaction();
        Transaction t2 = buildExistingTransaction();
        t2.setDescription("second");

        when(transactionRepository.findAll()).thenReturn(List.of(t1, t2));

        // when
        List<Transaction> all = transactionService.getAllTransactions();

        // then
        verify(transactionRepository).findAll();
        assertThat(all).hasSize(2);
        assertThat(all.get(1).getDescription()).isEqualTo("second");
    }

    @Test
    @DisplayName("ID-yə görə transaction tapılıb qaytarılmalıdır")
    void getTransactionById_whenFound_shouldReturn() {
        // given
        Transaction existing = buildExistingTransaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existing));

        // when
        Transaction result = transactionService.getTransactionById(transactionId);

        // then
        verify(transactionRepository).findById(transactionId);
        assertThat(result).isEqualTo(existing);
    }

    @Test
    @DisplayName("ID-yə görə transaction tapılmadıqda TransactionNotFoundException atılmalıdır")
    void getTransactionById_whenNotFound_shouldThrow() {
        // given
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.getTransactionById(transactionId));
        verify(transactionRepository).findById(transactionId);
    }

    @Test
    @DisplayName("Transaction yenilənməli və dəyişikliklər obyekt üzərində tətbiq edilməlidir")
    void updateTransaction_shouldMutateManagedEntityAndReturnIt() {
        // given
        Transaction existing = buildExistingTransaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existing));

        when(accountService.getAccountById(any(UUID.class))).thenReturn(anotherAccount);

        var request = new UpdateTransactionRequest(
                transactionId,
                TransactionType.DEPOSIT,
                new BigDecimal("999.99"),
                "updated desc",
                UUID.randomUUID()
        );

        // when
        Transaction updated = transactionService.updateTransaction(request);

        // then
        verify(transactionRepository).findById(transactionId);
        verify(accountService).getAccountById(request.accountId());
        verify(transactionRepository, never()).save(any(Transaction.class));

        assertThat(updated).isSameAs(existing);
        assertThat(updated.getAccount()).isEqualTo(anotherAccount);
        assertThat(updated.getAmount()).isEqualByComparingTo("999.99");
        assertThat(updated.getDescription()).isEqualTo("updated desc");
        assertThat(updated.getTransactionType()).isEqualTo(com.rahibjafar.account.model.TransactionType.DEPOSIT);
    }

    @Test
    @DisplayName("Transaction silinərkən status INACTIVE olaraq dəyişdirilməlidir")
    void deleteTransaction_shouldSetStatusInactive() {
        // given
        Transaction existing = buildExistingTransaction(); // status ACTIVE
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(existing));

        // when
        Transaction deleted = transactionService.deleteTransaction(transactionId);

        // then
        verify(transactionRepository).findById(transactionId);
        verify(transactionRepository, never()).save(any(Transaction.class));

        assertThat(deleted).isSameAs(existing);
        assertThat(deleted.getStatus()).isEqualTo(ModelStatus.INACTIVE);
    }
}
