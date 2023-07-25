package com.versatile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.versatile.IntegrationTest;
import com.versatile.domain.Inventory;
import com.versatile.repository.InventoryRepository;
import com.versatile.service.dto.InventoryDTO;
import com.versatile.service.mapper.InventoryMapper;
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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryResourceIT {

    private static final Integer DEFAULT_IN_STOCK = 1;
    private static final Integer UPDATED_IN_STOCK = 2;

    private static final Integer DEFAULT_BOOKED = 1;
    private static final Integer UPDATED_BOOKED = 2;

    private static final Integer DEFAULT_CLAIM = 1;
    private static final Integer UPDATED_CLAIM = 2;

    private static final String ENTITY_API_URL = "/api/inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory().inStock(DEFAULT_IN_STOCK).booked(DEFAULT_BOOKED).claim(DEFAULT_CLAIM);
        return inventory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory().inStock(UPDATED_IN_STOCK).booked(UPDATED_BOOKED).claim(UPDATED_CLAIM);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();
        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);
        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInStock()).isEqualTo(DEFAULT_IN_STOCK);
        assertThat(testInventory.getBooked()).isEqualTo(DEFAULT_BOOKED);
        assertThat(testInventory.getClaim()).isEqualTo(DEFAULT_CLAIM);
    }

    @Test
    @Transactional
    void createInventoryWithExistingId() throws Exception {
        // Create the Inventory with an existing ID
        inventory.setId(1L);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inStock").value(hasItem(DEFAULT_IN_STOCK)))
            .andExpect(jsonPath("$.[*].booked").value(hasItem(DEFAULT_BOOKED)))
            .andExpect(jsonPath("$.[*].claim").value(hasItem(DEFAULT_CLAIM)));
    }

    @Test
    @Transactional
    void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.inStock").value(DEFAULT_IN_STOCK))
            .andExpect(jsonPath("$.booked").value(DEFAULT_BOOKED))
            .andExpect(jsonPath("$.claim").value(DEFAULT_CLAIM));
    }

    @Test
    @Transactional
    void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory.inStock(UPDATED_IN_STOCK).booked(UPDATED_BOOKED).claim(UPDATED_CLAIM);
        InventoryDTO inventoryDTO = inventoryMapper.toDto(updatedInventory);

        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInStock()).isEqualTo(UPDATED_IN_STOCK);
        assertThat(testInventory.getBooked()).isEqualTo(UPDATED_BOOKED);
        assertThat(testInventory.getClaim()).isEqualTo(UPDATED_CLAIM);
    }

    @Test
    @Transactional
    void putNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory.inStock(UPDATED_IN_STOCK).claim(UPDATED_CLAIM);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInStock()).isEqualTo(UPDATED_IN_STOCK);
        assertThat(testInventory.getBooked()).isEqualTo(DEFAULT_BOOKED);
        assertThat(testInventory.getClaim()).isEqualTo(UPDATED_CLAIM);
    }

    @Test
    @Transactional
    void fullUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory.inStock(UPDATED_IN_STOCK).booked(UPDATED_BOOKED).claim(UPDATED_CLAIM);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInStock()).isEqualTo(UPDATED_IN_STOCK);
        assertThat(testInventory.getBooked()).isEqualTo(UPDATED_BOOKED);
        assertThat(testInventory.getClaim()).isEqualTo(UPDATED_CLAIM);
    }

    @Test
    @Transactional
    void patchNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();
        inventory.setId(count.incrementAndGet());

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.toDto(inventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
