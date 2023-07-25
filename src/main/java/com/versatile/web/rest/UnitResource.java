package com.versatile.web.rest;

import com.versatile.repository.UnitRepository;
import com.versatile.service.UnitService;
import com.versatile.service.dto.UnitDTO;
import com.versatile.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.versatile.domain.Unit}.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    private static final String ENTITY_NAME = "unit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitService unitService;

    private final UnitRepository unitRepository;

    public UnitResource(UnitService unitService, UnitRepository unitRepository) {
        this.unitService = unitService;
        this.unitRepository = unitRepository;
    }

    /**
     * {@code POST  /units} : Create a new unit.
     *
     * @param unitDTO the unitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitDTO, or with status {@code 400 (Bad Request)} if the unit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/units")
    public ResponseEntity<UnitDTO> createUnit(@RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unitDTO);
        if (unitDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitDTO result = unitService.save(unitDTO);
        return ResponseEntity
            .created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /units/:id} : Updates an existing unit.
     *
     * @param id the id of the unitDTO to save.
     * @param unitDTO the unitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitDTO,
     * or with status {@code 400 (Bad Request)} if the unitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/units/{id}")
    public ResponseEntity<UnitDTO> updateUnit(@PathVariable(value = "id", required = false) final Long id, @RequestBody UnitDTO unitDTO)
        throws URISyntaxException {
        log.debug("REST request to update Unit : {}, {}", id, unitDTO);
        if (unitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnitDTO result = unitService.update(unitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /units/:id} : Partial updates given fields of an existing unit, field will ignore if it is null
     *
     * @param id the id of the unitDTO to save.
     * @param unitDTO the unitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitDTO,
     * or with status {@code 400 (Bad Request)} if the unitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the unitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the unitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnitDTO> partialUpdateUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnitDTO unitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Unit partially : {}, {}", id, unitDTO);
        if (unitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnitDTO> result = unitService.partialUpdate(unitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /units} : get all the units.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of units in body.
     */
    @GetMapping("/units")
    public ResponseEntity<List<UnitDTO>> getAllUnits(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("inventory-is-null".equals(filter)) {
            log.debug("REST request to get all Units where inventory is null");
            return new ResponseEntity<>(unitService.findAllWhereInventoryIsNull(), HttpStatus.OK);
        }

        if ("partialobtain-is-null".equals(filter)) {
            log.debug("REST request to get all Units where partialObtain is null");
            return new ResponseEntity<>(unitService.findAllWherePartialObtainIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Units");
        Page<UnitDTO> page = unitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /units/:id} : get the "id" unit.
     *
     * @param id the id of the unitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/units/{id}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        Optional<UnitDTO> unitDTO = unitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitDTO);
    }

    /**
     * {@code DELETE  /units/:id} : delete the "id" unit.
     *
     * @param id the id of the unitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/units/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
