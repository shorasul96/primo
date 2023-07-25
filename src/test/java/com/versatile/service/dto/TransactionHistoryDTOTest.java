package com.versatile.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.versatile.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistoryDTO.class);
        TransactionHistoryDTO transactionHistoryDTO1 = new TransactionHistoryDTO();
        transactionHistoryDTO1.setId(1L);
        TransactionHistoryDTO transactionHistoryDTO2 = new TransactionHistoryDTO();
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO2.setId(transactionHistoryDTO1.getId());
        assertThat(transactionHistoryDTO1).isEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO2.setId(2L);
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO1.setId(null);
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
    }
}
