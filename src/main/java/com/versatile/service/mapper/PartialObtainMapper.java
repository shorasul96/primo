package com.versatile.service.mapper;

import com.versatile.domain.Customer;
import com.versatile.domain.Inventory;
import com.versatile.domain.PartialObtain;
import com.versatile.domain.Unit;
import com.versatile.service.dto.CustomerDTO;
import com.versatile.service.dto.InventoryDTO;
import com.versatile.service.dto.PartialObtainDTO;
import com.versatile.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartialObtain} and its DTO {@link PartialObtainDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartialObtainMapper extends EntityMapper<PartialObtainDTO, PartialObtain> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "item", source = "item", qualifiedByName = "inventoryId")
    PartialObtainDTO toDto(PartialObtain s);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("inventoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InventoryDTO toDtoInventoryId(Inventory inventory);
}
