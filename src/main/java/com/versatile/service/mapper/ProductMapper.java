package com.versatile.service.mapper;

import com.versatile.domain.Category;
import com.versatile.domain.ManufactureStage;
import com.versatile.domain.Product;
import com.versatile.service.dto.CategoryDTO;
import com.versatile.service.dto.ManufactureStageDTO;
import com.versatile.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "stage", source = "stage", qualifiedByName = "manufactureStageId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    ProductDTO toDto(Product s);

    @Named("manufactureStageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManufactureStageDTO toDtoManufactureStageId(ManufactureStage manufactureStage);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
