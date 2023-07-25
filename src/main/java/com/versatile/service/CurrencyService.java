package com.versatile.service;

import com.versatile.service.dto.CurrencyDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.versatile.domain.Currency}.
 */
public interface CurrencyService {
    /**
     * Save a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyDTO save(CurrencyDTO currencyDTO);

    /**
     * Updates a currency.
     *
     * @param currencyDTO the entity to update.
     * @return the persisted entity.
     */
    CurrencyDTO update(CurrencyDTO currencyDTO);

    /**
     * Partially updates a currency.
     *
     * @param currencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CurrencyDTO> partialUpdate(CurrencyDTO currencyDTO);

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurrencyDTO> findAll(Pageable pageable);

    /**
     * Get all the CurrencyDTO where Balance is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CurrencyDTO> findAllWhereBalanceIsNull();
    /**
     * Get all the CurrencyDTO where TransactionHistory is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CurrencyDTO> findAllWhereTransactionHistoryIsNull();

    /**
     * Get the "id" currency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyDTO> findOne(Long id);

    /**
     * Delete the "id" currency.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
