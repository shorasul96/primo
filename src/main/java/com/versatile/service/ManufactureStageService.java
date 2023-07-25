package com.versatile.service;

import com.versatile.service.dto.ManufactureStageDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.ManufactureStage}.
 */
public interface ManufactureStageService {
    /**
     * Save a manufactureStage.
     *
     * @param manufactureStageDTO the entity to save.
     * @return the persisted entity.
     */
    ManufactureStageDTO save(ManufactureStageDTO manufactureStageDTO);

    /**
     * Updates a manufactureStage.
     *
     * @param manufactureStageDTO the entity to update.
     * @return the persisted entity.
     */
    ManufactureStageDTO update(ManufactureStageDTO manufactureStageDTO);

    /**
     * Partially updates a manufactureStage.
     *
     * @param manufactureStageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManufactureStageDTO> partialUpdate(ManufactureStageDTO manufactureStageDTO);

    /**
     * Get all the manufactureStages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManufactureStageDTO> findAll(Pageable pageable);

    /**
     * Get all the ManufactureStageDTO where Product is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ManufactureStageDTO> findAllWhereProductIsNull();

    /**
     * Get the "id" manufactureStage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManufactureStageDTO> findOne(Long id);

    /**
     * Delete the "id" manufactureStage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
