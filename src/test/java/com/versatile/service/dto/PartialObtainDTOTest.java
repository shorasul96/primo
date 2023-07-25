package com.versatile.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartialObtainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartialObtainDTO.class);
        PartialObtainDTO partialObtainDTO1 = new PartialObtainDTO();
        partialObtainDTO1.setId(1L);
        PartialObtainDTO partialObtainDTO2 = new PartialObtainDTO();
        assertThat(partialObtainDTO1).isNotEqualTo(partialObtainDTO2);
        partialObtainDTO2.setId(partialObtainDTO1.getId());
        assertThat(partialObtainDTO1).isEqualTo(partialObtainDTO2);
        partialObtainDTO2.setId(2L);
        assertThat(partialObtainDTO1).isNotEqualTo(partialObtainDTO2);
        partialObtainDTO1.setId(null);
        assertThat(partialObtainDTO1).isNotEqualTo(partialObtainDTO2);
    }
}
