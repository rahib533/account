package com.rahibjafar.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountRequest(
        BigDecimal balance,
        UUID customerId
) {
}
