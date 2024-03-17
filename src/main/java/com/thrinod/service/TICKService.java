package com.thrinod.service;

import com.thrinod.domain.TICK;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.thrinod.domain.TICK}.
 */
public interface TICKService {
    /**
     * Save a tICK.
     *
     * @param tICK the entity to save.
     * @return the persisted entity.
     */
    TICK save(TICK tICK);

    /**
     * Updates a tICK.
     *
     * @param tICK the entity to update.
     * @return the persisted entity.
     */
    TICK update(TICK tICK);

    /**
     * Partially updates a tICK.
     *
     * @param tICK the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TICK> partialUpdate(TICK tICK);

    /**
     * Get all the tICKS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TICK> findAll(Pageable pageable);

    /**
     * Get the "id" tICK.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TICK> findOne(Long id);

    /**
     * Delete the "id" tICK.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
