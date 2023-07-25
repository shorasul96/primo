package com.versatile.service.mapper;

import com.versatile.domain.Customer;
import com.versatile.domain.Marketing;
import com.versatile.domain.Product;
import com.versatile.service.dto.CustomerDTO;
import com.versatile.service.dto.MarketingDTO;
import com.versatile.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Marketing} and its DTO {@link MarketingDTO}.
 */
@Mapper(componentModel = "spring")
public interface MarketingMapper extends EntityMapper<MarketingDTO, Marketing> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    MarketingDTO toDto(Marketing s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
