package com.rahibjafar.account.mapper;

import com.rahibjafar.account.dto.AccountDto;
import com.rahibjafar.account.model.Account;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto accountDto);
    List<AccountDto> toDto(List<Account> accounts);
    List<Account> toEntity(List<AccountDto> accountDtos);
}
