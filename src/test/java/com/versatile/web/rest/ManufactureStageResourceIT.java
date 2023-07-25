package com.versatile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.versatile.IntegrationTest;
import com.versatile.domain.ManufactureStage;
import com.versatile.repository.ManufactureStageRepository;
import com.versatile.service.dto.ManufactureStageDTO;
import com.versatile.service.mapper.ManufactureStageMapper;
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
 * Integration tests for the {@link ManufactureStageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManufactureStageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/manufacture-stages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManufactureStageRepository manufactureStageRepository;

    @Autowired
    private ManufactureStageMapper manufactureStageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManufactureStageMockMvc;

    private ManufactureStage manufactureStage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManufactureStage createEntity(EntityManager em) {
        ManufactureStage manufactureStage = new ManufactureStage().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return manufactureStage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManufactureStage createUpdatedEntity(EntityManager em) {
        ManufactureStage manufactureStage = new ManufactureStage().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return manufactureStage;
    }

    @BeforeEach
    public void initTest() {
        manufactureStage = createEntity(em);
    }

    @Test
    @Transactional
    void createManufactureStage() throws Exception {
        int databaseSizeBeforeCreate = manufactureStageRepository.findAll().size();
        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);
        restManufactureStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeCreate + 1);
        ManufactureStage testManufactureStage = manufactureStageList.get(manufactureStageList.size() - 1);
        assertThat(testManufactureStage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufactureStage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createManufactureStageWithExistingId() throws Exception {
        // Create the ManufactureStage with an existing ID
        manufactureStage.setId(1L);
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        int databaseSizeBeforeCreate = manufactureStageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufactureStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManufactureStages() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        // Get all the manufactureStageList
        restManufactureStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufactureStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getManufactureStage() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        // Get the manufactureStage
        restManufactureStageMockMvc
            .perform(get(ENTITY_API_URL_ID, manufactureStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manufactureStage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingManufactureStage() throws Exception {
        // Get the manufactureStage
        restManufactureStageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingManufactureStage() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();

        // Update the manufactureStage
        ManufactureStage updatedManufactureStage = manufactureStageRepository.findById(manufactureStage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedManufactureStage are not directly saved in db
        em.detach(updatedManufactureStage);
        updatedManufactureStage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(updatedManufactureStage);

        restManufactureStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manufactureStageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
        ManufactureStage testManufactureStage = manufactureStageList.get(manufactureStageList.size() - 1);
        assertThat(testManufactureStage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufactureStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manufactureStageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManufactureStageWithPatch() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();

        // Update the manufactureStage using partial update
        ManufactureStage partialUpdatedManufactureStage = new ManufactureStage();
        partialUpdatedManufactureStage.setId(manufactureStage.getId());

        partialUpdatedManufactureStage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restManufactureStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufactureStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManufactureStage))
            )
            .andExpect(status().isOk());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
        ManufactureStage testManufactureStage = manufactureStageList.get(manufactureStageList.size() - 1);
        assertThat(testManufactureStage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufactureStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateManufactureStageWithPatch() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();

        // Update the manufactureStage using partial update
        ManufactureStage partialUpdatedManufactureStage = new ManufactureStage();
        partialUpdatedManufactureStage.setId(manufactureStage.getId());

        partialUpdatedManufactureStage.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restManufactureStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufactureStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManufactureStage))
            )
            .andExpect(status().isOk());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
        ManufactureStage testManufactureStage = manufactureStageList.get(manufactureStageList.size() - 1);
        assertThat(testManufactureStage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufactureStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, manufactureStageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManufactureStage() throws Exception {
        int databaseSizeBeforeUpdate = manufactureStageRepository.findAll().size();
        manufactureStage.setId(count.incrementAndGet());

        // Create the ManufactureStage
        ManufactureStageDTO manufactureStageDTO = manufactureStageMapper.toDto(manufactureStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufactureStageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manufactureStageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManufactureStage in the database
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManufactureStage() throws Exception {
        // Initialize the database
        manufactureStageRepository.saveAndFlush(manufactureStage);

        int databaseSizeBeforeDelete = manufactureStageRepository.findAll().size();

        // Delete the manufactureStage
        restManufactureStageMockMvc
            .perform(delete(ENTITY_API_URL_ID, manufactureStage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ManufactureStage> manufactureStageList = manufactureStageRepository.findAll();
        assertThat(manufactureStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
