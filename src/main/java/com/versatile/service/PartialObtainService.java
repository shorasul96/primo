package com.versatile.service;

import com.versatile.service.dto.PartialObtainDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.PartialObtain}.
 */
public interface PartialObtainService {
    /**
     * Save a partialObtain.
     *
     * @param partialObtainDTO the entity to save.
     * @return the persisted entity.
     */
    PartialObtainDTO save(PartialObtainDTO partialObtainDTO);

    /**
     * Updates a partialObtain.
     *
     * @param partialObtainDTO the entity to update.
     * @return the persisted entity.
     */
    PartialObtainDTO update(PartialObtainDTO partialObtainDTO);

    /**
     * Partially updates a partialObtain.
     *
     * @param partialObtainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PartialObtainDTO> partialUpdate(PartialObtainDTO partialObtainDTO);

    /**
     * Get all the partialObtains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartialObtainDTO> findAll(Pageable pageable);

    /**
     * Get the "id" partialObtain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartialObtainDTO> findOne(Long id);

    /**
     * Delete the "id" partialObtain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
