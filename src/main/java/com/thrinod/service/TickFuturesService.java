package com.thrinod.service;

import com.thrinod.domain.TickFutures;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.thrinod.domain.TickFutures}.
 */
public interface TickFuturesService {
    /**
     * Save a tickFutures.
     *
     * @param tickFutures the entity to save.
     * @return the persisted entity.
     */
    TickFutures save(TickFutures tickFutures);

    /**
     * Updates a tickFutures.
     *
     * @param tickFutures the entity to update.
     * @return the persisted entity.
     */
    TickFutures update(TickFutures tickFutures);

    /**
     * Partially updates a tickFutures.
     *
     * @param tickFutures the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TickFutures> partialUpdate(TickFutures tickFutures);

    /**
     * Get all the tickFutures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TickFutures> findAll(Pageable pageable);

    /**
     * Get the "id" tickFutures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TickFutures> findOne(Long id);

    /**
     * Delete the "id" tickFutures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
