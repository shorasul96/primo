package com.versatile.service.impl;

import com.versatile.domain.PartialObtain;
import com.versatile.repository.PartialObtainRepository;
import com.versatile.service.PartialObtainService;
import com.versatile.service.dto.PartialObtainDTO;
import com.versatile.service.mapper.PartialObtainMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PartialObtain}.
 */
@Service
@Transactional
public class PartialObtainServiceImpl implements PartialObtainService {

    private final Logger log = LoggerFactory.getLogger(PartialObtainServiceImpl.class);

    private final PartialObtainRepository partialObtainRepository;

    private final PartialObtainMapper partialObtainMapper;

    public PartialObtainServiceImpl(PartialObtainRepository partialObtainRepository, PartialObtainMapper partialObtainMapper) {
        this.partialObtainRepository = partialObtainRepository;
        this.partialObtainMapper = partialObtainMapper;
    }

    @Override
    public PartialObtainDTO save(PartialObtainDTO partialObtainDTO) {
        log.debug("Request to save PartialObtain : {}", partialObtainDTO);
        PartialObtain partialObtain = partialObtainMapper.toEntity(partialObtainDTO);
        partialObtain = partialObtainRepository.save(partialObtain);
        return partialObtainMapper.toDto(partialObtain);
    }

    @Override
    public PartialObtainDTO update(PartialObtainDTO partialObtainDTO) {
        log.debug("Request to update PartialObtain : {}", partialObtainDTO);
        PartialObtain partialObtain = partialObtainMapper.toEntity(partialObtainDTO);
        partialObtain = partialObtainRepository.save(partialObtain);
        return partialObtainMapper.toDto(partialObtain);
    }

    @Override
    public Optional<PartialObtainDTO> partialUpdate(PartialObtainDTO partialObtainDTO) {
        log.debug("Request to partially update PartialObtain : {}", partialObtainDTO);

        return partialObtainRepository
            .findById(partialObtainDTO.getId())
            .map(existingPartialObtain -> {
                partialObtainMapper.partialUpdate(existingPartialObtain, partialObtainDTO);

                return existingPartialObtain;
            })
            .map(partialObtainRepository::save)
            .map(partialObtainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartialObtainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PartialObtains");
        return partialObtainRepository.findAll(pageable).map(partialObtainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PartialObtainDTO> findOne(Long id) {
        log.debug("Request to get PartialObtain : {}", id);
        return partialObtainRepository.findById(id).map(partialObtainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartialObtain : {}", id);
        partialObtainRepository.deleteById(id);
    }
}
