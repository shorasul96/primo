package com.versatile.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartialObtainMapperTest {

    private PartialObtainMapper partialObtainMapper;

    @BeforeEach
    public void setUp() {
        partialObtainMapper = new PartialObtainMapperImpl();
    }
}
