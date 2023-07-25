package com.versatile.web.rest;

import com.versatile.repository.MarketingRepository;
import com.versatile.service.MarketingService;
import com.versatile.service.dto.MarketingDTO;
import com.versatile.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.versatile.domain.Marketing}.
 */
@RestController
@RequestMapping("/api")
public class MarketingResource {

    private final Logger log = LoggerFactory.getLogger(MarketingResource.class);

    private static final String ENTITY_NAME = "marketing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarketingService marketingService;

    private final MarketingRepository marketingRepository;

    public MarketingResource(MarketingService marketingService, MarketingRepository marketingRepository) {
        this.marketingService = marketingService;
        this.marketingRepository = marketingRepository;
    }

    /**
     * {@code POST  /marketings} : Create a new marketing.
     *
     * @param marketingDTO the marketingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marketingDTO, or with status {@code 400 (Bad Request)} if the marketing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marketings")
    public ResponseEntity<MarketingDTO> createMarketing(@RequestBody MarketingDTO marketingDTO) throws URISyntaxException {
        log.debug("REST request to save Marketing : {}", marketingDTO);
        if (marketingDTO.getId() != null) {
            throw new BadRequestAlertException("A new marketing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketingDTO result = marketingService.save(marketingDTO);
        return ResponseEntity
            .created(new URI("/api/marketings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marketings/:id} : Updates an existing marketing.
     *
     * @param id the id of the marketingDTO to save.
     * @param marketingDTO the marketingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketingDTO,
     * or with status {@code 400 (Bad Request)} if the marketingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marketingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marketings/{id}")
    public ResponseEntity<MarketingDTO> updateMarketing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MarketingDTO marketingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Marketing : {}, {}", id, marketingDTO);
        if (marketingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MarketingDTO result = marketingService.update(marketingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /marketings/:id} : Partial updates given fields of an existing marketing, field will ignore if it is null
     *
     * @param id the id of the marketingDTO to save.
     * @param marketingDTO the marketingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marketingDTO,
     * or with status {@code 400 (Bad Request)} if the marketingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the marketingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the marketingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/marketings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MarketingDTO> partialUpdateMarketing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MarketingDTO marketingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Marketing partially : {}, {}", id, marketingDTO);
        if (marketingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marketingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marketingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MarketingDTO> result = marketingService.partialUpdate(marketingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marketingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /marketings} : get all the marketings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marketings in body.
     */
    @GetMapping("/marketings")
    public ResponseEntity<List<MarketingDTO>> getAllMarketings(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Marketings");
        Page<MarketingDTO> page = marketingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marketings/:id} : get the "id" marketing.
     *
     * @param id the id of the marketingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marketings/{id}")
    public ResponseEntity<MarketingDTO> getMarketing(@PathVariable Long id) {
        log.debug("REST request to get Marketing : {}", id);
        Optional<MarketingDTO> marketingDTO = marketingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marketingDTO);
    }

    /**
     * {@code DELETE  /marketings/:id} : delete the "id" marketing.
     *
     * @param id the id of the marketingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marketings/{id}")
    public ResponseEntity<Void> deleteMarketing(@PathVariable Long id) {
        log.debug("REST request to delete Marketing : {}", id);
        marketingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
