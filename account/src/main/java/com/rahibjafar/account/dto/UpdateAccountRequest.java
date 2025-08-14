package com.rahibjafar.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateAccountRequest(
        UUID id,
        BigDecimal balance,
        UUID customerId
) {
}
