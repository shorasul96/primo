package com.versatile.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManufactureStageMapperTest {

    private ManufactureStageMapper manufactureStageMapper;

    @BeforeEach
    public void setUp() {
        manufactureStageMapper = new ManufactureStageMapperImpl();
    }
}
