package com.versatile.service;

import com.versatile.service.dto.BalanceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.Balance}.
 */
public interface BalanceService {
    /**
     * Save a balance.
     *
     * @param balanceDTO the entity to save.
     * @return the persisted entity.
     */
    BalanceDTO save(BalanceDTO balanceDTO);

    /**
     * Updates a balance.
     *
     * @param balanceDTO the entity to update.
     * @return the persisted entity.
     */
    BalanceDTO update(BalanceDTO balanceDTO);

    /**
     * Partially updates a balance.
     *
     * @param balanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BalanceDTO> partialUpdate(BalanceDTO balanceDTO);

    /**
     * Get all the balances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BalanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" balance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BalanceDTO> findOne(Long id);

    /**
     * Delete the "id" balance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
