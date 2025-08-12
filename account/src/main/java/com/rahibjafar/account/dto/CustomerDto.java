package com.rahibjafar.account.dto;

import com.rahibjafar.account.model.Account;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CustomerDto {
    private UUID id;

    private String cif;

    private String firstName;

    private String lastName;

    private Set<Account> accounts;
}
