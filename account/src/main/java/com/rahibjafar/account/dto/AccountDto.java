package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.ModelStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountDto(
        UUID id,
        BigDecimal balance,
        LocalDateTime creationDate,
        CustomerDto customer,
        ModelStatus status
) {
}
