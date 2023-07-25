package com.versatile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.versatile.IntegrationTest;
import com.versatile.domain.PartialObtain;
import com.versatile.repository.PartialObtainRepository;
import com.versatile.service.dto.PartialObtainDTO;
import com.versatile.service.mapper.PartialObtainMapper;
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
 * Integration tests for the {@link PartialObtainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartialObtainResourceIT {

    private static final String ENTITY_API_URL = "/api/partial-obtains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PartialObtainRepository partialObtainRepository;

    @Autowired
    private PartialObtainMapper partialObtainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartialObtainMockMvc;

    private PartialObtain partialObtain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartialObtain createEntity(EntityManager em) {
        PartialObtain partialObtain = new PartialObtain();
        return partialObtain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartialObtain createUpdatedEntity(EntityManager em) {
        PartialObtain partialObtain = new PartialObtain();
        return partialObtain;
    }

    @BeforeEach
    public void initTest() {
        partialObtain = createEntity(em);
    }

    @Test
    @Transactional
    void createPartialObtain() throws Exception {
        int databaseSizeBeforeCreate = partialObtainRepository.findAll().size();
        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);
        restPartialObtainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeCreate + 1);
        PartialObtain testPartialObtain = partialObtainList.get(partialObtainList.size() - 1);
    }

    @Test
    @Transactional
    void createPartialObtainWithExistingId() throws Exception {
        // Create the PartialObtain with an existing ID
        partialObtain.setId(1L);
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        int databaseSizeBeforeCreate = partialObtainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartialObtainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartialObtains() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        // Get all the partialObtainList
        restPartialObtainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partialObtain.getId().intValue())));
    }

    @Test
    @Transactional
    void getPartialObtain() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        // Get the partialObtain
        restPartialObtainMockMvc
            .perform(get(ENTITY_API_URL_ID, partialObtain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partialObtain.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPartialObtain() throws Exception {
        // Get the partialObtain
        restPartialObtainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartialObtain() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();

        // Update the partialObtain
        PartialObtain updatedPartialObtain = partialObtainRepository.findById(partialObtain.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartialObtain are not directly saved in db
        em.detach(updatedPartialObtain);
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(updatedPartialObtain);

        restPartialObtainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partialObtainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isOk());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
        PartialObtain testPartialObtain = partialObtainList.get(partialObtainList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partialObtainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartialObtainWithPatch() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();

        // Update the partialObtain using partial update
        PartialObtain partialUpdatedPartialObtain = new PartialObtain();
        partialUpdatedPartialObtain.setId(partialObtain.getId());

        restPartialObtainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartialObtain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartialObtain))
            )
            .andExpect(status().isOk());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
        PartialObtain testPartialObtain = partialObtainList.get(partialObtainList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePartialObtainWithPatch() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();

        // Update the partialObtain using partial update
        PartialObtain partialUpdatedPartialObtain = new PartialObtain();
        partialUpdatedPartialObtain.setId(partialObtain.getId());

        restPartialObtainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartialObtain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPartialObtain))
            )
            .andExpect(status().isOk());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
        PartialObtain testPartialObtain = partialObtainList.get(partialObtainList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialObtainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartialObtain() throws Exception {
        int databaseSizeBeforeUpdate = partialObtainRepository.findAll().size();
        partialObtain.setId(count.incrementAndGet());

        // Create the PartialObtain
        PartialObtainDTO partialObtainDTO = partialObtainMapper.toDto(partialObtain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartialObtainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialObtainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PartialObtain in the database
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartialObtain() throws Exception {
        // Initialize the database
        partialObtainRepository.saveAndFlush(partialObtain);

        int databaseSizeBeforeDelete = partialObtainRepository.findAll().size();

        // Delete the partialObtain
        restPartialObtainMockMvc
            .perform(delete(ENTITY_API_URL_ID, partialObtain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartialObtain> partialObtainList = partialObtainRepository.findAll();
        assertThat(partialObtainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
