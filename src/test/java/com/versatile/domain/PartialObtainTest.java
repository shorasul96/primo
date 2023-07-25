package com.versatile.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartialObtainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartialObtain.class);
        PartialObtain partialObtain1 = new PartialObtain();
        partialObtain1.setId(1L);
        PartialObtain partialObtain2 = new PartialObtain();
        partialObtain2.setId(partialObtain1.getId());
        assertThat(partialObtain1).isEqualTo(partialObtain2);
        partialObtain2.setId(2L);
        assertThat(partialObtain1).isNotEqualTo(partialObtain2);
        partialObtain1.setId(null);
        assertThat(partialObtain1).isNotEqualTo(partialObtain2);
    }
}
