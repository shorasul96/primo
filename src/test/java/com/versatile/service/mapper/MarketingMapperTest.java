package com.versatile.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarketingMapperTest {

    private MarketingMapper marketingMapper;

    @BeforeEach
    public void setUp() {
        marketingMapper = new MarketingMapperImpl();
    }
}
