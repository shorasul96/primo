package com.versatile.service.impl;

import com.versatile.domain.Balance;
import com.versatile.repository.BalanceRepository;
import com.versatile.service.BalanceService;
import com.versatile.service.dto.BalanceDTO;
import com.versatile.service.mapper.BalanceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Balance}.
 */
@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {

    private final Logger log = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BalanceRepository balanceRepository;

    private final BalanceMapper balanceMapper;

    public BalanceServiceImpl(BalanceRepository balanceRepository, BalanceMapper balanceMapper) {
        this.balanceRepository = balanceRepository;
        this.balanceMapper = balanceMapper;
    }

    @Override
    public BalanceDTO save(BalanceDTO balanceDTO) {
        log.debug("Request to save Balance : {}", balanceDTO);
        Balance balance = balanceMapper.toEntity(balanceDTO);
        balance = balanceRepository.save(balance);
        return balanceMapper.toDto(balance);
    }

    @Override
    public BalanceDTO update(BalanceDTO balanceDTO) {
        log.debug("Request to update Balance : {}", balanceDTO);
        Balance balance = balanceMapper.toEntity(balanceDTO);
        balance = balanceRepository.save(balance);
        return balanceMapper.toDto(balance);
    }

    @Override
    public Optional<BalanceDTO> partialUpdate(BalanceDTO balanceDTO) {
        log.debug("Request to partially update Balance : {}", balanceDTO);

        return balanceRepository
            .findById(balanceDTO.getId())
            .map(existingBalance -> {
                balanceMapper.partialUpdate(existingBalance, balanceDTO);

                return existingBalance;
            })
            .map(balanceRepository::save)
            .map(balanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BalanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Balances");
        return balanceRepository.findAll(pageable).map(balanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BalanceDTO> findOne(Long id) {
        log.debug("Request to get Balance : {}", id);
        return balanceRepository.findById(id).map(balanceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Balance : {}", id);
        balanceRepository.deleteById(id);
    }
}
