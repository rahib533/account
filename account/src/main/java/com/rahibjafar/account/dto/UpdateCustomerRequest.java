package com.rahibjafar.account.dto;

import java.util.UUID;

public record UpdateCustomerRequest(
        UUID id,
        String cif,
        String firstName,
        String lastName
) {
}
