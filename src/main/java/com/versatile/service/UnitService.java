package com.versatile.service;

import com.versatile.service.dto.UnitDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.Unit}.
 */
public interface UnitService {
    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    UnitDTO save(UnitDTO unitDTO);

    /**
     * Updates a unit.
     *
     * @param unitDTO the entity to update.
     * @return the persisted entity.
     */
    UnitDTO update(UnitDTO unitDTO);

    /**
     * Partially updates a unit.
     *
     * @param unitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UnitDTO> partialUpdate(UnitDTO unitDTO);

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitDTO> findAll(Pageable pageable);

    /**
     * Get all the UnitDTO where Inventory is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UnitDTO> findAllWhereInventoryIsNull();
    /**
     * Get all the UnitDTO where PartialObtain is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UnitDTO> findAllWherePartialObtainIsNull();

    /**
     * Get the "id" unit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitDTO> findOne(Long id);

    /**
     * Delete the "id" unit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
