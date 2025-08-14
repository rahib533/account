package com.rahibjafar.account.dto;

public record CreateCustomerRequest(
        String cif,
        String firstName,
        String lastName
) {
}
