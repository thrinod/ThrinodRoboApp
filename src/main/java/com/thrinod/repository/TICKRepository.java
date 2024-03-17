package com.thrinod.repository;

import com.thrinod.domain.TICK;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TICK entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TICKRepository extends JpaRepository<TICK, Long> {}
