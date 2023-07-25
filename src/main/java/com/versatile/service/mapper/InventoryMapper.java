package com.versatile.service.mapper;

import com.versatile.domain.Inventory;
import com.versatile.domain.Product;
import com.versatile.domain.Unit;
import com.versatile.service.dto.InventoryDTO;
import com.versatile.service.dto.ProductDTO;
import com.versatile.service.dto.UnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "unitId")
    InventoryDTO toDto(Inventory s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("unitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UnitDTO toDtoUnitId(Unit unit);
}
