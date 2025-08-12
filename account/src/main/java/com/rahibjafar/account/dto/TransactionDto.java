package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.Account;
import com.rahibjafar.account.model.TransactionType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class TransactionDto {
    private UUID id;

    private TransactionType transactionType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime creationDate;

    private Account account;
}
