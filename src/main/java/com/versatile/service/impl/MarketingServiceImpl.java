package com.versatile.service.impl;

import com.versatile.domain.Marketing;
import com.versatile.repository.MarketingRepository;
import com.versatile.service.MarketingService;
import com.versatile.service.dto.MarketingDTO;
import com.versatile.service.mapper.MarketingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Marketing}.
 */
@Service
@Transactional
public class MarketingServiceImpl implements MarketingService {

    private final Logger log = LoggerFactory.getLogger(MarketingServiceImpl.class);

    private final MarketingRepository marketingRepository;

    private final MarketingMapper marketingMapper;

    public MarketingServiceImpl(MarketingRepository marketingRepository, MarketingMapper marketingMapper) {
        this.marketingRepository = marketingRepository;
        this.marketingMapper = marketingMapper;
    }

    @Override
    public MarketingDTO save(MarketingDTO marketingDTO) {
        log.debug("Request to save Marketing : {}", marketingDTO);
        Marketing marketing = marketingMapper.toEntity(marketingDTO);
        marketing = marketingRepository.save(marketing);
        return marketingMapper.toDto(marketing);
    }

    @Override
    public MarketingDTO update(MarketingDTO marketingDTO) {
        log.debug("Request to update Marketing : {}", marketingDTO);
        Marketing marketing = marketingMapper.toEntity(marketingDTO);
        marketing = marketingRepository.save(marketing);
        return marketingMapper.toDto(marketing);
    }

    @Override
    public Optional<MarketingDTO> partialUpdate(MarketingDTO marketingDTO) {
        log.debug("Request to partially update Marketing : {}", marketingDTO);

        return marketingRepository
            .findById(marketingDTO.getId())
            .map(existingMarketing -> {
                marketingMapper.partialUpdate(existingMarketing, marketingDTO);

                return existingMarketing;
            })
            .map(marketingRepository::save)
            .map(marketingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarketingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Marketings");
        return marketingRepository.findAll(pageable).map(marketingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MarketingDTO> findOne(Long id) {
        log.debug("Request to get Marketing : {}", id);
        return marketingRepository.findById(id).map(marketingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marketing : {}", id);
        marketingRepository.deleteById(id);
    }
}
