package com.thrinod.repository;

import com.thrinod.domain.TickStock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TickStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickStockRepository extends JpaRepository<TickStock, Long> {}
