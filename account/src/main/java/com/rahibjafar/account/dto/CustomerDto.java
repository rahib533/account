package com.rahibjafar.account.dto;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String cif,
        String firstName,
        String lastName
) {
}
