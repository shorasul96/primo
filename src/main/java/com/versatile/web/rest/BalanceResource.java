package com.versatile.web.rest;

import com.versatile.repository.BalanceRepository;
import com.versatile.service.BalanceService;
import com.versatile.service.dto.BalanceDTO;
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
 * REST controller for managing {@link com.versatile.domain.Balance}.
 */
@RestController
@RequestMapping("/api")
public class BalanceResource {

    private final Logger log = LoggerFactory.getLogger(BalanceResource.class);

    private static final String ENTITY_NAME = "balance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalanceService balanceService;

    private final BalanceRepository balanceRepository;

    public BalanceResource(BalanceService balanceService, BalanceRepository balanceRepository) {
        this.balanceService = balanceService;
        this.balanceRepository = balanceRepository;
    }

    /**
     * {@code POST  /balances} : Create a new balance.
     *
     * @param balanceDTO the balanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new balanceDTO, or with status {@code 400 (Bad Request)} if the balance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/balances")
    public ResponseEntity<BalanceDTO> createBalance(@RequestBody BalanceDTO balanceDTO) throws URISyntaxException {
        log.debug("REST request to save Balance : {}", balanceDTO);
        if (balanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new balance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceDTO result = balanceService.save(balanceDTO);
        return ResponseEntity
            .created(new URI("/api/balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /balances/:id} : Updates an existing balance.
     *
     * @param id the id of the balanceDTO to save.
     * @param balanceDTO the balanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balanceDTO,
     * or with status {@code 400 (Bad Request)} if the balanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the balanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/balances/{id}")
    public ResponseEntity<BalanceDTO> updateBalance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BalanceDTO balanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Balance : {}, {}", id, balanceDTO);
        if (balanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BalanceDTO result = balanceService.update(balanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, balanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /balances/:id} : Partial updates given fields of an existing balance, field will ignore if it is null
     *
     * @param id the id of the balanceDTO to save.
     * @param balanceDTO the balanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balanceDTO,
     * or with status {@code 400 (Bad Request)} if the balanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the balanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the balanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/balances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BalanceDTO> partialUpdateBalance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BalanceDTO balanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Balance partially : {}, {}", id, balanceDTO);
        if (balanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BalanceDTO> result = balanceService.partialUpdate(balanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, balanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /balances} : get all the balances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of balances in body.
     */
    @GetMapping("/balances")
    public ResponseEntity<List<BalanceDTO>> getAllBalances(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Balances");
        Page<BalanceDTO> page = balanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /balances/:id} : get the "id" balance.
     *
     * @param id the id of the balanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the balanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/balances/{id}")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long id) {
        log.debug("REST request to get Balance : {}", id);
        Optional<BalanceDTO> balanceDTO = balanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(balanceDTO);
    }

    /**
     * {@code DELETE  /balances/:id} : delete the "id" balance.
     *
     * @param id the id of the balanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/balances/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        log.debug("REST request to delete Balance : {}", id);
        balanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
