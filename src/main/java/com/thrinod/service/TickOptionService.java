package com.thrinod.service;

import com.thrinod.domain.TickOption;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.thrinod.domain.TickOption}.
 */
public interface TickOptionService {
    /**
     * Save a tickOption.
     *
     * @param tickOption the entity to save.
     * @return the persisted entity.
     */
    TickOption save(TickOption tickOption);

    /**
     * Updates a tickOption.
     *
     * @param tickOption the entity to update.
     * @return the persisted entity.
     */
    TickOption update(TickOption tickOption);

    /**
     * Partially updates a tickOption.
     *
     * @param tickOption the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TickOption> partialUpdate(TickOption tickOption);

    /**
     * Get all the tickOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TickOption> findAll(Pageable pageable);

    /**
     * Get the "id" tickOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TickOption> findOne(Long id);

    /**
     * Delete the "id" tickOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
