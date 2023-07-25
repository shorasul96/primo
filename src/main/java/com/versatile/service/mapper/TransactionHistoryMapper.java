package com.versatile.service.mapper;

import com.versatile.domain.Currency;
import com.versatile.domain.Customer;
import com.versatile.domain.TransactionHistory;
import com.versatile.service.dto.CurrencyDTO;
import com.versatile.service.dto.CustomerDTO;
import com.versatile.service.dto.TransactionHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionHistory} and its DTO {@link TransactionHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionHistoryMapper extends EntityMapper<TransactionHistoryDTO, TransactionHistory> {
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currencyId")
    @Mapping(target = "client", source = "client", qualifiedByName = "customerId")
    TransactionHistoryDTO toDto(TransactionHistory s);

    @Named("currencyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrencyDTO toDtoCurrencyId(Currency currency);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
