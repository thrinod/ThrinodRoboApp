package com.thrinod.web.rest;

import com.thrinod.domain.TickIndex;
import com.thrinod.repository.TickIndexRepository;
import com.thrinod.service.TickIndexService;
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
 * REST controller for managing {@link com.thrinod.domain.TickIndex}.
 */
@RestController
@RequestMapping("/api/tick-indices")
public class TickIndexResource {

    private final Logger log = LoggerFactory.getLogger(TickIndexResource.class);

    private static final String ENTITY_NAME = "tickIndex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TickIndexService tickIndexService;

    private final TickIndexRepository tickIndexRepository;

    public TickIndexResource(TickIndexService tickIndexService, TickIndexRepository tickIndexRepository) {
        this.tickIndexService = tickIndexService;
        this.tickIndexRepository = tickIndexRepository;
    }

    /**
     * {@code POST  /tick-indices} : Create a new tickIndex.
     *
     * @param tickIndex the tickIndex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tickIndex, or with status {@code 400 (Bad Request)} if the tickIndex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TickIndex> createTickIndex(@RequestBody TickIndex tickIndex) throws URISyntaxException {
        log.debug("REST request to save TickIndex : {}", tickIndex);
        if (tickIndex.getId() != null) {
            throw new BadRequestAlertException("A new tickIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TickIndex result = tickIndexService.save(tickIndex);
        return ResponseEntity
            .created(new URI("/api/tick-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tick-indices/:id} : Updates an existing tickIndex.
     *
     * @param id the id of the tickIndex to save.
     * @param tickIndex the tickIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickIndex,
     * or with status {@code 400 (Bad Request)} if the tickIndex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tickIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TickIndex> updateTickIndex(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickIndex tickIndex
    ) throws URISyntaxException {
        log.debug("REST request to update TickIndex : {}, {}", id, tickIndex);
        if (tickIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickIndex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickIndexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TickIndex result = tickIndexService.update(tickIndex);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickIndex.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tick-indices/:id} : Partial updates given fields of an existing tickIndex, field will ignore if it is null
     *
     * @param id the id of the tickIndex to save.
     * @param tickIndex the tickIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickIndex,
     * or with status {@code 400 (Bad Request)} if the tickIndex is not valid,
     * or with status {@code 404 (Not Found)} if the tickIndex is not found,
     * or with status {@code 500 (Internal Server Error)} if the tickIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TickIndex> partialUpdateTickIndex(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickIndex tickIndex
    ) throws URISyntaxException {
        log.debug("REST request to partial update TickIndex partially : {}, {}", id, tickIndex);
        if (tickIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickIndex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickIndexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TickIndex> result = tickIndexService.partialUpdate(tickIndex);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickIndex.getId().toString())
        );
    }

    /**
     * {@code GET  /tick-indices} : get all the tickIndices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickIndices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TickIndex>> getAllTickIndices(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TickIndices");
        Page<TickIndex> page = tickIndexService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tick-indices/:id} : get the "id" tickIndex.
     *
     * @param id the id of the tickIndex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tickIndex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TickIndex> getTickIndex(@PathVariable("id") Long id) {
        log.debug("REST request to get TickIndex : {}", id);
        Optional<TickIndex> tickIndex = tickIndexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tickIndex);
    }

    /**
     * {@code DELETE  /tick-indices/:id} : delete the "id" tickIndex.
     *
     * @param id the id of the tickIndex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTickIndex(@PathVariable("id") Long id) {
        log.debug("REST request to delete TickIndex : {}", id);
        tickIndexService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
