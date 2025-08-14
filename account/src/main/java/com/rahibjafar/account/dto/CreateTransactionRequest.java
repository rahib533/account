package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.TransactionType;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(
        TransactionType transactionType,
        BigDecimal amount,
        String description,
        UUID accountId
) {
}
