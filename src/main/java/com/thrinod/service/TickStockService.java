package com.thrinod.service;

import com.thrinod.domain.TickStock;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.thrinod.domain.TickStock}.
 */
public interface TickStockService {
    /**
     * Save a tickStock.
     *
     * @param tickStock the entity to save.
     * @return the persisted entity.
     */
    TickStock save(TickStock tickStock);

    /**
     * Updates a tickStock.
     *
     * @param tickStock the entity to update.
     * @return the persisted entity.
     */
    TickStock update(TickStock tickStock);

    /**
     * Partially updates a tickStock.
     *
     * @param tickStock the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TickStock> partialUpdate(TickStock tickStock);

    /**
     * Get all the tickStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TickStock> findAll(Pageable pageable);

    /**
     * Get the "id" tickStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TickStock> findOne(Long id);

    /**
     * Delete the "id" tickStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
