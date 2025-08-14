package com.rahibjafar.account.mapper;

import com.rahibjafar.account.dto.CreateCustomerRequest;
import com.rahibjafar.account.dto.CustomerDto;
import com.rahibjafar.account.dto.UpdateCustomerRequest;
import com.rahibjafar.account.model.Customer;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
    List<CustomerDto> toDto(List<Customer> customers);
    List<Customer> toEntity(List<CustomerDto> customerDtos);
    Customer toEntity(CreateCustomerRequest createCustomerRequest);
}
