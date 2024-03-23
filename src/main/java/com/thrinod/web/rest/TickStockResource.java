package com.thrinod.web.rest;

import com.thrinod.domain.TickStock;
import com.thrinod.repository.TickStockRepository;
import com.thrinod.service.TickStockService;
import com.thrinod.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.thrinod.domain.TickStock}.
 */
@RestController
@RequestMapping("/api/tick-stocks")
public class TickStockResource {

    private final Logger log = LoggerFactory.getLogger(TickStockResource.class);

    private static final String ENTITY_NAME = "tickStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TickStockService tickStockService;

    private final TickStockRepository tickStockRepository;

    public TickStockResource(TickStockService tickStockService, TickStockRepository tickStockRepository) {
        this.tickStockService = tickStockService;
        this.tickStockRepository = tickStockRepository;
    }

    /**
     * {@code POST  /tick-stocks} : Create a new tickStock.
     *
     * @param tickStock the tickStock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tickStock, or with status {@code 400 (Bad Request)} if the tickStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TickStock> createTickStock(@RequestBody TickStock tickStock) throws URISyntaxException {
        log.debug("REST request to save TickStock : {}", tickStock);
        if (tickStock.getId() != null) {
            throw new BadRequestAlertException("A new tickStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TickStock result = tickStockService.save(tickStock);
        return ResponseEntity
            .created(new URI("/api/tick-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tick-stocks/:id} : Updates an existing tickStock.
     *
     * @param id the id of the tickStock to save.
     * @param tickStock the tickStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickStock,
     * or with status {@code 400 (Bad Request)} if the tickStock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tickStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TickStock> updateTickStock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickStock tickStock
    ) throws URISyntaxException {
        log.debug("REST request to update TickStock : {}, {}", id, tickStock);
        if (tickStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TickStock result = tickStockService.update(tickStock);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickStock.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tick-stocks/:id} : Partial updates given fields of an existing tickStock, field will ignore if it is null
     *
     * @param id the id of the tickStock to save.
     * @param tickStock the tickStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickStock,
     * or with status {@code 400 (Bad Request)} if the tickStock is not valid,
     * or with status {@code 404 (Not Found)} if the tickStock is not found,
     * or with status {@code 500 (Internal Server Error)} if the tickStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TickStock> partialUpdateTickStock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickStock tickStock
    ) throws URISyntaxException {
        log.debug("REST request to partial update TickStock partially : {}, {}", id, tickStock);
        if (tickStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TickStock> result = tickStockService.partialUpdate(tickStock);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickStock.getId().toString())
        );
    }

    /**
     * {@code GET  /tick-stocks} : get all the tickStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickStocks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TickStock>> getAllTickStocks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TickStocks");
        Page<TickStock> page = tickStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tick-stocks/:id} : get the "id" tickStock.
     *
     * @param id the id of the tickStock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tickStock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TickStock> getTickStock(@PathVariable("id") Long id) {
        log.debug("REST request to get TickStock : {}", id);
        Optional<TickStock> tickStock = tickStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tickStock);
    }

    /**
     * {@code DELETE  /tick-stocks/:id} : delete the "id" tickStock.
     *
     * @param id the id of the tickStock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTickStock(@PathVariable("id") Long id) {
        log.debug("REST request to delete TickStock : {}", id);
        tickStockService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
