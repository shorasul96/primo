package com.versatile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.versatile.IntegrationTest;
import com.versatile.domain.Balance;
import com.versatile.repository.BalanceRepository;
import com.versatile.service.dto.BalanceDTO;
import com.versatile.service.mapper.BalanceMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BalanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BalanceResourceIT {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    private static final Float DEFAULT_LOAN = 1F;
    private static final Float UPDATED_LOAN = 2F;

    private static final String ENTITY_API_URL = "/api/balances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBalanceMockMvc;

    private Balance balance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balance createEntity(EntityManager em) {
        Balance balance = new Balance().amount(DEFAULT_AMOUNT).loan(DEFAULT_LOAN);
        return balance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Balance createUpdatedEntity(EntityManager em) {
        Balance balance = new Balance().amount(UPDATED_AMOUNT).loan(UPDATED_LOAN);
        return balance;
    }

    @BeforeEach
    public void initTest() {
        balance = createEntity(em);
    }

    @Test
    @Transactional
    void createBalance() throws Exception {
        int databaseSizeBeforeCreate = balanceRepository.findAll().size();
        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);
        restBalanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeCreate + 1);
        Balance testBalance = balanceList.get(balanceList.size() - 1);
        assertThat(testBalance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBalance.getLoan()).isEqualTo(DEFAULT_LOAN);
    }

    @Test
    @Transactional
    void createBalanceWithExistingId() throws Exception {
        // Create the Balance with an existing ID
        balance.setId(1L);
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        int databaseSizeBeforeCreate = balanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBalances() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        // Get all the balanceList
        restBalanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balance.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loan").value(hasItem(DEFAULT_LOAN.doubleValue())));
    }

    @Test
    @Transactional
    void getBalance() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        // Get the balance
        restBalanceMockMvc
            .perform(get(ENTITY_API_URL_ID, balance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(balance.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loan").value(DEFAULT_LOAN.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingBalance() throws Exception {
        // Get the balance
        restBalanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBalance() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();

        // Update the balance
        Balance updatedBalance = balanceRepository.findById(balance.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBalance are not directly saved in db
        em.detach(updatedBalance);
        updatedBalance.amount(UPDATED_AMOUNT).loan(UPDATED_LOAN);
        BalanceDTO balanceDTO = balanceMapper.toDto(updatedBalance);

        restBalanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
        Balance testBalance = balanceList.get(balanceList.size() - 1);
        assertThat(testBalance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBalance.getLoan()).isEqualTo(UPDATED_LOAN);
    }

    @Test
    @Transactional
    void putNonExistingBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, balanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(balanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBalanceWithPatch() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();

        // Update the balance using partial update
        Balance partialUpdatedBalance = new Balance();
        partialUpdatedBalance.setId(balance.getId());

        restBalanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBalance))
            )
            .andExpect(status().isOk());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
        Balance testBalance = balanceList.get(balanceList.size() - 1);
        assertThat(testBalance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBalance.getLoan()).isEqualTo(DEFAULT_LOAN);
    }

    @Test
    @Transactional
    void fullUpdateBalanceWithPatch() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();

        // Update the balance using partial update
        Balance partialUpdatedBalance = new Balance();
        partialUpdatedBalance.setId(balance.getId());

        partialUpdatedBalance.amount(UPDATED_AMOUNT).loan(UPDATED_LOAN);

        restBalanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBalance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBalance))
            )
            .andExpect(status().isOk());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
        Balance testBalance = balanceList.get(balanceList.size() - 1);
        assertThat(testBalance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBalance.getLoan()).isEqualTo(UPDATED_LOAN);
    }

    @Test
    @Transactional
    void patchNonExistingBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, balanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBalance() throws Exception {
        int databaseSizeBeforeUpdate = balanceRepository.findAll().size();
        balance.setId(count.incrementAndGet());

        // Create the Balance
        BalanceDTO balanceDTO = balanceMapper.toDto(balance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBalanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(balanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Balance in the database
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBalance() throws Exception {
        // Initialize the database
        balanceRepository.saveAndFlush(balance);

        int databaseSizeBeforeDelete = balanceRepository.findAll().size();

        // Delete the balance
        restBalanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, balance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Balance> balanceList = balanceRepository.findAll();
        assertThat(balanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
