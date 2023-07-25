package com.versatile.web.rest;

import com.versatile.repository.ManufactureStageRepository;
import com.versatile.service.ManufactureStageService;
import com.versatile.service.dto.ManufactureStageDTO;
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
 * REST controller for managing {@link com.versatile.domain.ManufactureStage}.
 */
@RestController
@RequestMapping("/api")
public class ManufactureStageResource {

    private final Logger log = LoggerFactory.getLogger(ManufactureStageResource.class);

    private static final String ENTITY_NAME = "manufactureStage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManufactureStageService manufactureStageService;

    private final ManufactureStageRepository manufactureStageRepository;

    public ManufactureStageResource(
        ManufactureStageService manufactureStageService,
        ManufactureStageRepository manufactureStageRepository
    ) {
        this.manufactureStageService = manufactureStageService;
        this.manufactureStageRepository = manufactureStageRepository;
    }

    /**
     * {@code POST  /manufacture-stages} : Create a new manufactureStage.
     *
     * @param manufactureStageDTO the manufactureStageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manufactureStageDTO, or with status {@code 400 (Bad Request)} if the manufactureStage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/manufacture-stages")
    public ResponseEntity<ManufactureStageDTO> createManufactureStage(@RequestBody ManufactureStageDTO manufactureStageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ManufactureStage : {}", manufactureStageDTO);
        if (manufactureStageDTO.getId() != null) {
            throw new BadRequestAlertException("A new manufactureStage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManufactureStageDTO result = manufactureStageService.save(manufactureStageDTO);
        return ResponseEntity
            .created(new URI("/api/manufacture-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /manufacture-stages/:id} : Updates an existing manufactureStage.
     *
     * @param id the id of the manufactureStageDTO to save.
     * @param manufactureStageDTO the manufactureStageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufactureStageDTO,
     * or with status {@code 400 (Bad Request)} if the manufactureStageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manufactureStageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/manufacture-stages/{id}")
    public ResponseEntity<ManufactureStageDTO> updateManufactureStage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManufactureStageDTO manufactureStageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ManufactureStage : {}, {}", id, manufactureStageDTO);
        if (manufactureStageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufactureStageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufactureStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManufactureStageDTO result = manufactureStageService.update(manufactureStageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufactureStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /manufacture-stages/:id} : Partial updates given fields of an existing manufactureStage, field will ignore if it is null
     *
     * @param id the id of the manufactureStageDTO to save.
     * @param manufactureStageDTO the manufactureStageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manufactureStageDTO,
     * or with status {@code 400 (Bad Request)} if the manufactureStageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the manufactureStageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the manufactureStageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/manufacture-stages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManufactureStageDTO> partialUpdateManufactureStage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManufactureStageDTO manufactureStageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManufactureStage partially : {}, {}", id, manufactureStageDTO);
        if (manufactureStageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manufactureStageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manufactureStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManufactureStageDTO> result = manufactureStageService.partialUpdate(manufactureStageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manufactureStageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /manufacture-stages} : get all the manufactureStages.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manufactureStages in body.
     */
    @GetMapping("/manufacture-stages")
    public ResponseEntity<List<ManufactureStageDTO>> getAllManufactureStages(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("product-is-null".equals(filter)) {
            log.debug("REST request to get all ManufactureStages where product is null");
            return new ResponseEntity<>(manufactureStageService.findAllWhereProductIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ManufactureStages");
        Page<ManufactureStageDTO> page = manufactureStageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /manufacture-stages/:id} : get the "id" manufactureStage.
     *
     * @param id the id of the manufactureStageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manufactureStageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/manufacture-stages/{id}")
    public ResponseEntity<ManufactureStageDTO> getManufactureStage(@PathVariable Long id) {
        log.debug("REST request to get ManufactureStage : {}", id);
        Optional<ManufactureStageDTO> manufactureStageDTO = manufactureStageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manufactureStageDTO);
    }

    /**
     * {@code DELETE  /manufacture-stages/:id} : delete the "id" manufactureStage.
     *
     * @param id the id of the manufactureStageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/manufacture-stages/{id}")
    public ResponseEntity<Void> deleteManufactureStage(@PathVariable Long id) {
        log.debug("REST request to delete ManufactureStage : {}", id);
        manufactureStageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
