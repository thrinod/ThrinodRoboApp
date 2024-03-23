package com.thrinod.repository;

import com.thrinod.domain.TickIndex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TickIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickIndexRepository extends JpaRepository<TickIndex, Long> {}
