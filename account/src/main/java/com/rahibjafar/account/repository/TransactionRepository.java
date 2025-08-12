package com.rahibjafar.account.repository;

import com.rahibjafar.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
