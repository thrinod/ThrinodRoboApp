package com.thrinod.repository;

import com.thrinod.domain.TickOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TickOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TickOptionRepository extends JpaRepository<TickOption, Long> {}
