package com.rahibjafar.account.mapper;

import com.rahibjafar.account.dto.TransactionDto;
import com.rahibjafar.account.model.Transaction;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
    List<TransactionDto> toDto(List<Transaction> transactions);
    List<Transaction> toEntity(List<TransactionDto> transactionDtos);
}
