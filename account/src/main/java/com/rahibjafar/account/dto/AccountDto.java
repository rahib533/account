package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.Customer;
import com.rahibjafar.account.model.Transaction;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountDto {
    private UUID id;

    private BigDecimal balance;

    private LocalDateTime creationDate;

    private Customer customer;

    private Set<Transaction> transactions;
}
