package com.versatile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.versatile.IntegrationTest;
import com.versatile.domain.Marketing;
import com.versatile.domain.enumeration.DealType;
import com.versatile.repository.MarketingRepository;
import com.versatile.service.dto.MarketingDTO;
import com.versatile.service.mapper.MarketingMapper;
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
 * Integration tests for the {@link MarketingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarketingResourceIT {

    private static final DealType DEFAULT_DEAL = DealType.SALE;
    private static final DealType UPDATED_DEAL = DealType.TRADE;

    private static final String ENTITY_API_URL = "/api/marketings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarketingRepository marketingRepository;

    @Autowired
    private MarketingMapper marketingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarketingMockMvc;

    private Marketing marketing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marketing createEntity(EntityManager em) {
        Marketing marketing = new Marketing().deal(DEFAULT_DEAL);
        return marketing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marketing createUpdatedEntity(EntityManager em) {
        Marketing marketing = new Marketing().deal(UPDATED_DEAL);
        return marketing;
    }

    @BeforeEach
    public void initTest() {
        marketing = createEntity(em);
    }

    @Test
    @Transactional
    void createMarketing() throws Exception {
        int databaseSizeBeforeCreate = marketingRepository.findAll().size();
        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);
        restMarketingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketingDTO)))
            .andExpect(status().isCreated());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeCreate + 1);
        Marketing testMarketing = marketingList.get(marketingList.size() - 1);
        assertThat(testMarketing.getDeal()).isEqualTo(DEFAULT_DEAL);
    }

    @Test
    @Transactional
    void createMarketingWithExistingId() throws Exception {
        // Create the Marketing with an existing ID
        marketing.setId(1L);
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        int databaseSizeBeforeCreate = marketingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMarketings() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        // Get all the marketingList
        restMarketingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketing.getId().intValue())))
            .andExpect(jsonPath("$.[*].deal").value(hasItem(DEFAULT_DEAL.toString())));
    }

    @Test
    @Transactional
    void getMarketing() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        // Get the marketing
        restMarketingMockMvc
            .perform(get(ENTITY_API_URL_ID, marketing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marketing.getId().intValue()))
            .andExpect(jsonPath("$.deal").value(DEFAULT_DEAL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMarketing() throws Exception {
        // Get the marketing
        restMarketingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMarketing() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();

        // Update the marketing
        Marketing updatedMarketing = marketingRepository.findById(marketing.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMarketing are not directly saved in db
        em.detach(updatedMarketing);
        updatedMarketing.deal(UPDATED_DEAL);
        MarketingDTO marketingDTO = marketingMapper.toDto(updatedMarketing);

        restMarketingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
        Marketing testMarketing = marketingList.get(marketingList.size() - 1);
        assertThat(testMarketing.getDeal()).isEqualTo(UPDATED_DEAL);
    }

    @Test
    @Transactional
    void putNonExistingMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marketingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marketingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarketingWithPatch() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();

        // Update the marketing using partial update
        Marketing partialUpdatedMarketing = new Marketing();
        partialUpdatedMarketing.setId(marketing.getId());

        restMarketingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketing))
            )
            .andExpect(status().isOk());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
        Marketing testMarketing = marketingList.get(marketingList.size() - 1);
        assertThat(testMarketing.getDeal()).isEqualTo(DEFAULT_DEAL);
    }

    @Test
    @Transactional
    void fullUpdateMarketingWithPatch() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();

        // Update the marketing using partial update
        Marketing partialUpdatedMarketing = new Marketing();
        partialUpdatedMarketing.setId(marketing.getId());

        partialUpdatedMarketing.deal(UPDATED_DEAL);

        restMarketingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarketing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarketing))
            )
            .andExpect(status().isOk());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
        Marketing testMarketing = marketingList.get(marketingList.size() - 1);
        assertThat(testMarketing.getDeal()).isEqualTo(UPDATED_DEAL);
    }

    @Test
    @Transactional
    void patchNonExistingMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marketingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarketing() throws Exception {
        int databaseSizeBeforeUpdate = marketingRepository.findAll().size();
        marketing.setId(count.incrementAndGet());

        // Create the Marketing
        MarketingDTO marketingDTO = marketingMapper.toDto(marketing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarketingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(marketingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marketing in the database
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarketing() throws Exception {
        // Initialize the database
        marketingRepository.saveAndFlush(marketing);

        int databaseSizeBeforeDelete = marketingRepository.findAll().size();

        // Delete the marketing
        restMarketingMockMvc
            .perform(delete(ENTITY_API_URL_ID, marketing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Marketing> marketingList = marketingRepository.findAll();
        assertThat(marketingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
