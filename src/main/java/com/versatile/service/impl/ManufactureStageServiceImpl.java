package com.versatile.service.impl;

import com.versatile.domain.ManufactureStage;
import com.versatile.repository.ManufactureStageRepository;
import com.versatile.service.ManufactureStageService;
import com.versatile.service.dto.ManufactureStageDTO;
import com.versatile.service.mapper.ManufactureStageMapper;
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
 * Service Implementation for managing {@link ManufactureStage}.
 */
@Service
@Transactional
public class ManufactureStageServiceImpl implements ManufactureStageService {

    private final Logger log = LoggerFactory.getLogger(ManufactureStageServiceImpl.class);

    private final ManufactureStageRepository manufactureStageRepository;

    private final ManufactureStageMapper manufactureStageMapper;

    public ManufactureStageServiceImpl(
        ManufactureStageRepository manufactureStageRepository,
        ManufactureStageMapper manufactureStageMapper
    ) {
        this.manufactureStageRepository = manufactureStageRepository;
        this.manufactureStageMapper = manufactureStageMapper;
    }

    @Override
    public ManufactureStageDTO save(ManufactureStageDTO manufactureStageDTO) {
        log.debug("Request to save ManufactureStage : {}", manufactureStageDTO);
        ManufactureStage manufactureStage = manufactureStageMapper.toEntity(manufactureStageDTO);
        manufactureStage = manufactureStageRepository.save(manufactureStage);
        return manufactureStageMapper.toDto(manufactureStage);
    }

    @Override
    public ManufactureStageDTO update(ManufactureStageDTO manufactureStageDTO) {
        log.debug("Request to update ManufactureStage : {}", manufactureStageDTO);
        ManufactureStage manufactureStage = manufactureStageMapper.toEntity(manufactureStageDTO);
        manufactureStage = manufactureStageRepository.save(manufactureStage);
        return manufactureStageMapper.toDto(manufactureStage);
    }

    @Override
    public Optional<ManufactureStageDTO> partialUpdate(ManufactureStageDTO manufactureStageDTO) {
        log.debug("Request to partially update ManufactureStage : {}", manufactureStageDTO);

        return manufactureStageRepository
            .findById(manufactureStageDTO.getId())
            .map(existingManufactureStage -> {
                manufactureStageMapper.partialUpdate(existingManufactureStage, manufactureStageDTO);

                return existingManufactureStage;
            })
            .map(manufactureStageRepository::save)
            .map(manufactureStageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManufactureStageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ManufactureStages");
        return manufactureStageRepository.findAll(pageable).map(manufactureStageMapper::toDto);
    }

    /**
     *  Get all the manufactureStages where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ManufactureStageDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all manufactureStages where Product is null");
        return StreamSupport
            .stream(manufactureStageRepository.findAll().spliterator(), false)
            .filter(manufactureStage -> manufactureStage.getProduct() == null)
            .map(manufactureStageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManufactureStageDTO> findOne(Long id) {
        log.debug("Request to get ManufactureStage : {}", id);
        return manufactureStageRepository.findById(id).map(manufactureStageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManufactureStage : {}", id);
        manufactureStageRepository.deleteById(id);
    }
}
