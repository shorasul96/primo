package com.versatile.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManufactureStageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManufactureStageDTO.class);
        ManufactureStageDTO manufactureStageDTO1 = new ManufactureStageDTO();
        manufactureStageDTO1.setId(1L);
        ManufactureStageDTO manufactureStageDTO2 = new ManufactureStageDTO();
        assertThat(manufactureStageDTO1).isNotEqualTo(manufactureStageDTO2);
        manufactureStageDTO2.setId(manufactureStageDTO1.getId());
        assertThat(manufactureStageDTO1).isEqualTo(manufactureStageDTO2);
        manufactureStageDTO2.setId(2L);
        assertThat(manufactureStageDTO1).isNotEqualTo(manufactureStageDTO2);
        manufactureStageDTO1.setId(null);
        assertThat(manufactureStageDTO1).isNotEqualTo(manufactureStageDTO2);
    }
}
