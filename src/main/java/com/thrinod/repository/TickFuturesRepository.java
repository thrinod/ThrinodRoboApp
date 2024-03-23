package com.thrinod.repository;

import com.thrinod.domain.TickFutures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TickFutures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickFuturesRepository extends JpaRepository<TickFutures, Long> {}
