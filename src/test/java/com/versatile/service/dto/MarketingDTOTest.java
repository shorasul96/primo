package com.versatile.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketingDTO.class);
        MarketingDTO marketingDTO1 = new MarketingDTO();
        marketingDTO1.setId(1L);
        MarketingDTO marketingDTO2 = new MarketingDTO();
        assertThat(marketingDTO1).isNotEqualTo(marketingDTO2);
        marketingDTO2.setId(marketingDTO1.getId());
        assertThat(marketingDTO1).isEqualTo(marketingDTO2);
        marketingDTO2.setId(2L);
        assertThat(marketingDTO1).isNotEqualTo(marketingDTO2);
        marketingDTO1.setId(null);
        assertThat(marketingDTO1).isNotEqualTo(marketingDTO2);
    }
}
