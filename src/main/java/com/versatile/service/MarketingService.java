package com.versatile.service;

import com.versatile.service.dto.MarketingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.Marketing}.
 */
public interface MarketingService {
    /**
     * Save a marketing.
     *
     * @param marketingDTO the entity to save.
     * @return the persisted entity.
     */
    MarketingDTO save(MarketingDTO marketingDTO);

    /**
     * Updates a marketing.
     *
     * @param marketingDTO the entity to update.
     * @return the persisted entity.
     */
    MarketingDTO update(MarketingDTO marketingDTO);

    /**
     * Partially updates a marketing.
     *
     * @param marketingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MarketingDTO> partialUpdate(MarketingDTO marketingDTO);

    /**
     * Get all the marketings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MarketingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" marketing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MarketingDTO> findOne(Long id);

    /**
     * Delete the "id" marketing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
