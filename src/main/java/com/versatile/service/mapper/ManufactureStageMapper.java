package com.versatile.service.mapper;

import com.versatile.domain.ManufactureStage;
import com.versatile.service.dto.ManufactureStageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ManufactureStage} and its DTO {@link ManufactureStageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManufactureStageMapper extends EntityMapper<ManufactureStageDTO, ManufactureStage> {}
