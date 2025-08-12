package com.rahibjafar.account.repository;

import com.rahibjafar.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
