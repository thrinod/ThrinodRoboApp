package com.thrinod.web.rest;

import com.thrinod.domain.TickOption;
import com.thrinod.repository.TickOptionRepository;
import com.thrinod.service.TickOptionService;
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
 * REST controller for managing {@link com.thrinod.domain.TickOption}.
 */
@RestController
@RequestMapping("/api/tick-options")
public class TickOptionResource {

    private final Logger log = LoggerFactory.getLogger(TickOptionResource.class);

    private static final String ENTITY_NAME = "tickOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TickOptionService tickOptionService;

    private final TickOptionRepository tickOptionRepository;

    public TickOptionResource(TickOptionService tickOptionService, TickOptionRepository tickOptionRepository) {
        this.tickOptionService = tickOptionService;
        this.tickOptionRepository = tickOptionRepository;
    }

    /**
     * {@code POST  /tick-options} : Create a new tickOption.
     *
     * @param tickOption the tickOption to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tickOption, or with status {@code 400 (Bad Request)} if the tickOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TickOption> createTickOption(@RequestBody TickOption tickOption) throws URISyntaxException {
        log.debug("REST request to save TickOption : {}", tickOption);
        if (tickOption.getId() != null) {
            throw new BadRequestAlertException("A new tickOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TickOption result = tickOptionService.save(tickOption);
        return ResponseEntity
            .created(new URI("/api/tick-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tick-options/:id} : Updates an existing tickOption.
     *
     * @param id the id of the tickOption to save.
     * @param tickOption the tickOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickOption,
     * or with status {@code 400 (Bad Request)} if the tickOption is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tickOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TickOption> updateTickOption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickOption tickOption
    ) throws URISyntaxException {
        log.debug("REST request to update TickOption : {}, {}", id, tickOption);
        if (tickOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickOption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TickOption result = tickOptionService.update(tickOption);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickOption.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tick-options/:id} : Partial updates given fields of an existing tickOption, field will ignore if it is null
     *
     * @param id the id of the tickOption to save.
     * @param tickOption the tickOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tickOption,
     * or with status {@code 400 (Bad Request)} if the tickOption is not valid,
     * or with status {@code 404 (Not Found)} if the tickOption is not found,
     * or with status {@code 500 (Internal Server Error)} if the tickOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TickOption> partialUpdateTickOption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TickOption tickOption
    ) throws URISyntaxException {
        log.debug("REST request to partial update TickOption partially : {}, {}", id, tickOption);
        if (tickOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tickOption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tickOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TickOption> result = tickOptionService.partialUpdate(tickOption);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tickOption.getId().toString())
        );
    }

    /**
     * {@code GET  /tick-options} : get all the tickOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickOptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TickOption>> getAllTickOptions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TickOptions");
        Page<TickOption> page = tickOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tick-options/:id} : get the "id" tickOption.
     *
     * @param id the id of the tickOption to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tickOption, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TickOption> getTickOption(@PathVariable("id") Long id) {
        log.debug("REST request to get TickOption : {}", id);
        Optional<TickOption> tickOption = tickOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tickOption);
    }

    /**
     * {@code DELETE  /tick-options/:id} : delete the "id" tickOption.
     *
     * @param id the id of the tickOption to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTickOption(@PathVariable("id") Long id) {
        log.debug("REST request to delete TickOption : {}", id);
        tickOptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
