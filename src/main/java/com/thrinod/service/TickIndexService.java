package com.thrinod.service;

import com.thrinod.domain.TickIndex;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.thrinod.domain.TickIndex}.
 */
public interface TickIndexService {
    /**
     * Save a tickIndex.
     *
     * @param tickIndex the entity to save.
     * @return the persisted entity.
     */
    TickIndex save(TickIndex tickIndex);

    /**
     * Updates a tickIndex.
     *
     * @param tickIndex the entity to update.
     * @return the persisted entity.
     */
    TickIndex update(TickIndex tickIndex);

    /**
     * Partially updates a tickIndex.
     *
     * @param tickIndex the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TickIndex> partialUpdate(TickIndex tickIndex);

    /**
     * Get all the tickIndices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TickIndex> findAll(Pageable pageable);

    /**
     * Get the "id" tickIndex.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TickIndex> findOne(Long id);

    /**
     * Delete the "id" tickIndex.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
