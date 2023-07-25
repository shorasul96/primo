package com.versatile.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marketing.class);
        Marketing marketing1 = new Marketing();
        marketing1.setId(1L);
        Marketing marketing2 = new Marketing();
        marketing2.setId(marketing1.getId());
        assertThat(marketing1).isEqualTo(marketing2);
        marketing2.setId(2L);
        assertThat(marketing1).isNotEqualTo(marketing2);
        marketing1.setId(null);
        assertThat(marketing1).isNotEqualTo(marketing2);
    }
}
