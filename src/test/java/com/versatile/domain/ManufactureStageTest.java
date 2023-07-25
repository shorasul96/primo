package com.versatile.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManufactureStageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManufactureStage.class);
        ManufactureStage manufactureStage1 = new ManufactureStage();
        manufactureStage1.setId(1L);
        ManufactureStage manufactureStage2 = new ManufactureStage();
        manufactureStage2.setId(manufactureStage1.getId());
        assertThat(manufactureStage1).isEqualTo(manufactureStage2);
        manufactureStage2.setId(2L);
        assertThat(manufactureStage1).isNotEqualTo(manufactureStage2);
        manufactureStage1.setId(null);
        assertThat(manufactureStage1).isNotEqualTo(manufactureStage2);
    }
}
