package com.versatile.service.impl;

import com.versatile.domain.Currency;
import com.versatile.repository.CurrencyRepository;
import com.versatile.service.CurrencyService;
import com.versatile.service.dto.CurrencyDTO;
import com.versatile.service.mapper.CurrencyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Currency}.
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public CurrencyDTO save(CurrencyDTO currencyDTO) {
        log.debug("Request to save Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);
        currency = currencyRepository.save(currency);
        return currencyMapper.toDto(currency);
    }

    @Override
    public CurrencyDTO update(CurrencyDTO currencyDTO) {
        log.debug("Request to update Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);
        currency = currencyRepository.save(currency);
        return currencyMapper.toDto(currency);
    }

    @Override
    public Optional<CurrencyDTO> partialUpdate(CurrencyDTO currencyDTO) {
        log.debug("Request to partially update Currency : {}", currencyDTO);

        return currencyRepository
            .findById(currencyDTO.getId())
            .map(existingCurrency -> {
                currencyMapper.partialUpdate(existingCurrency, currencyDTO);

                return existingCurrency;
            })
            .map(currencyRepository::save)
            .map(currencyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        return currencyRepository.findAll(pageable).map(currencyMapper::toDto);
    }

    /**
     *  Get all the currencies where Balance is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyDTO> findAllWhereBalanceIsNull() {
        log.debug("Request to get all currencies where Balance is null");
        return StreamSupport
            .stream(currencyRepository.findAll().spliterator(), false)
            .filter(currency -> currency.getBalance() == null)
            .map(currencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the currencies where TransactionHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyDTO> findAllWhereTransactionHistoryIsNull() {
        log.debug("Request to get all currencies where TransactionHistory is null");
        return StreamSupport
            .stream(currencyRepository.findAll().spliterator(), false)
            .filter(currency -> currency.getTransactionHistory() == null)
            .map(currencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyDTO> findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        return currencyRepository.findById(id).map(currencyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.deleteById(id);
    }
}
