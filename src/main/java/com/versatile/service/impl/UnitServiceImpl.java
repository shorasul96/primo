package com.versatile.service.impl;

import com.versatile.domain.Unit;
import com.versatile.repository.UnitRepository;
import com.versatile.service.UnitService;
import com.versatile.service.dto.UnitDTO;
import com.versatile.service.mapper.UnitMapper;
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
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitServiceImpl(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    @Override
    public UnitDTO update(UnitDTO unitDTO) {
        log.debug("Request to update Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    @Override
    public Optional<UnitDTO> partialUpdate(UnitDTO unitDTO) {
        log.debug("Request to partially update Unit : {}", unitDTO);

        return unitRepository
            .findById(unitDTO.getId())
            .map(existingUnit -> {
                unitMapper.partialUpdate(existingUnit, unitDTO);

                return existingUnit;
            })
            .map(unitRepository::save)
            .map(unitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable).map(unitMapper::toDto);
    }

    /**
     *  Get all the units where Inventory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UnitDTO> findAllWhereInventoryIsNull() {
        log.debug("Request to get all units where Inventory is null");
        return StreamSupport
            .stream(unitRepository.findAll().spliterator(), false)
            .filter(unit -> unit.getInventory() == null)
            .map(unitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the units where PartialObtain is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UnitDTO> findAllWherePartialObtainIsNull() {
        log.debug("Request to get all units where PartialObtain is null");
        return StreamSupport
            .stream(unitRepository.findAll().spliterator(), false)
            .filter(unit -> unit.getPartialObtain() == null)
            .map(unitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UnitDTO> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id).map(unitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
