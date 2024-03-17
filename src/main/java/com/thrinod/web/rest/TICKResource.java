package com.thrinod.web.rest;

import com.thrinod.domain.TICK;
import com.thrinod.repository.TICKRepository;
import com.thrinod.service.TICKService;
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
 * REST controller for managing {@link com.thrinod.domain.TICK}.
 */
@RestController
@RequestMapping("/api/ticks")
public class TICKResource {

    private final Logger log = LoggerFactory.getLogger(TICKResource.class);

    private static final String ENTITY_NAME = "tICK";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TICKService tICKService;

    private final TICKRepository tICKRepository;

    public TICKResource(TICKService tICKService, TICKRepository tICKRepository) {
        this.tICKService = tICKService;
        this.tICKRepository = tICKRepository;
    }

    /**
     * {@code POST  /ticks} : Create a new tICK.
     *
     * @param tICK the tICK to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tICK, or with status {@code 400 (Bad Request)} if the tICK has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TICK> createTICK(@RequestBody TICK tICK) throws URISyntaxException {
        log.debug("REST request to save TICK : {}", tICK);
        if (tICK.getId() != null) {
            throw new BadRequestAlertException("A new tICK cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TICK result = tICKService.save(tICK);
        return ResponseEntity
            .created(new URI("/api/ticks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticks/:id} : Updates an existing tICK.
     *
     * @param id the id of the tICK to save.
     * @param tICK the tICK to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tICK,
     * or with status {@code 400 (Bad Request)} if the tICK is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tICK couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TICK> updateTICK(@PathVariable(value = "id", required = false) final Long id, @RequestBody TICK tICK)
        throws URISyntaxException {
        log.debug("REST request to update TICK : {}, {}", id, tICK);
        if (tICK.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tICK.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tICKRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TICK result = tICKService.update(tICK);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tICK.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ticks/:id} : Partial updates given fields of an existing tICK, field will ignore if it is null
     *
     * @param id the id of the tICK to save.
     * @param tICK the tICK to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tICK,
     * or with status {@code 400 (Bad Request)} if the tICK is not valid,
     * or with status {@code 404 (Not Found)} if the tICK is not found,
     * or with status {@code 500 (Internal Server Error)} if the tICK couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TICK> partialUpdateTICK(@PathVariable(value = "id", required = false) final Long id, @RequestBody TICK tICK)
        throws URISyntaxException {
        log.debug("REST request to partial update TICK partially : {}, {}", id, tICK);
        if (tICK.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tICK.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tICKRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TICK> result = tICKService.partialUpdate(tICK);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tICK.getId().toString())
        );
    }

    /**
     * {@code GET  /ticks} : get all the tICKS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tICKS in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TICK>> getAllTICKS(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TICKS");
        Page<TICK> page = tICKService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticks/:id} : get the "id" tICK.
     *
     * @param id the id of the tICK to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tICK, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TICK> getTICK(@PathVariable("id") Long id) {
        log.debug("REST request to get TICK : {}", id);
        Optional<TICK> tICK = tICKService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tICK);
    }

    /**
     * {@code DELETE  /ticks/:id} : delete the "id" tICK.
     *
     * @param id the id of the tICK to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTICK(@PathVariable("id") Long id) {
        log.debug("REST request to delete TICK : {}", id);
        tICKService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
