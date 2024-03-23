package com.thrinod.web.rest;

import com.thrinod.domain.TickFutures;
import com.thrinod.repository.TickFuturesRepository;
import com.thrinod.service.TickFuturesService;
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
 * REST controller for managing {@link com.thrinod.domain.TickFutures}.
 */
@RestController
@RequestMapping("/api/tick-futures")
public class TickFuturesResource {

    private final Logger log = LoggerFactory.getLogger(TickFuturesResource.class);

    private static final String ENTITY_NAME = "tickFutures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TickFuturesService tickFuturesService;

    private final TickFuturesRepository tickFuturesRepository;

    public TickFuturesResource(TickFuturesService tickFuturesService, TickFuturesRepository tickFuturesRepository) {
        this.tickFuturesService = tickFuturesService;
        this.tickFuturesRepository = tickFuturesRepository;
    }

    /**
     * {@code POST  /tick-futures} : Create a new tickFutures.
     *
     * @param tickFutures the tickFutures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tickFutures, or with status {@code 400 (Bad Request)} if the tickFutures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TickFutures> createTickFutures(@RequestBody TickFutures tickFutures) throws URISyntaxException {
        log.debug("REST request to save TickFutures : {}", tickFutures);
        if (tickFutures.getId() != null) {
            throw new BadRequestAlertException("A new tickFutures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TickFutures result = tickFuturesService.save(tickFutures);
        return ResponseEntity
            .created(new URI("/api/tick-futures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tick-futures/:id} : Updates an existing tickFutures.
     *
     * @param id the id of the tickFutures to save.
     * @param tickFutures the tickFutures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickFutures,
     * or with status {@code 400 (Bad Request)} if the tickFutures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tickFutures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TickFutures> updateTickFutures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickFutures tickFutures
    ) throws URISyntaxException {
        log.debug("REST request to update TickFutures : {}, {}", id, tickFutures);
        if (tickFutures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickFutures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickFuturesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TickFutures result = tickFuturesService.update(tickFutures);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickFutures.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tick-futures/:id} : Partial updates given fields of an existing tickFutures, field will ignore if it is null
     *
     * @param id the id of the tickFutures to save.
     * @param tickFutures the tickFutures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickFutures,
     * or with status {@code 400 (Bad Request)} if the tickFutures is not valid,
     * or with status {@code 404 (Not Found)} if the tickFutures is not found,
     * or with status {@code 500 (Internal Server Error)} if the tickFutures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TickFutures> partialUpdateTickFutures(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickFutures tickFutures
    ) throws URISyntaxException {
        log.debug("REST request to partial update TickFutures partially : {}, {}", id, tickFutures);
        if (tickFutures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickFutures.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickFuturesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TickFutures> result = tickFuturesService.partialUpdate(tickFutures);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickFutures.getId().toString())
        );
    }

    /**
     * {@code GET  /tick-futures} : get all the tickFutures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickFutures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TickFutures>> getAllTickFutures(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TickFutures");
        Page<TickFutures> page = tickFuturesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tick-futures/:id} : get the "id" tickFutures.
     *
     * @param id the id of the tickFutures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tickFutures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TickFutures> getTickFutures(@PathVariable("id") Long id) {
        log.debug("REST request to get TickFutures : {}", id);
        Optional<TickFutures> tickFutures = tickFuturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tickFutures);
    }

    /**
     * {@code DELETE  /tick-futures/:id} : delete the "id" tickFutures.
     *
     * @param id the id of the tickFutures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTickFutures(@PathVariable("id") Long id) {
        log.debug("REST request to delete TickFutures : {}", id);
        tickFuturesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
