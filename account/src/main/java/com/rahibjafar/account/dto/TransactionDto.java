package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto(
        UUID id,
        TransactionType transactionType,
        BigDecimal amount,
        String description,
        LocalDateTime creationDate,
        AccountDto account
) {
}
