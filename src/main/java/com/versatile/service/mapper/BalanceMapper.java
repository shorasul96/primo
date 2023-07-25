package com.versatile.service.mapper;

import com.versatile.domain.Balance;
import com.versatile.domain.Currency;
import com.versatile.service.dto.BalanceDTO;
import com.versatile.service.dto.CurrencyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Balance} and its DTO {@link BalanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface BalanceMapper extends EntityMapper<BalanceDTO, Balance> {
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currencyId")
    BalanceDTO toDto(Balance s);

    @Named("currencyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrencyDTO toDtoCurrencyId(Currency currency);
}
