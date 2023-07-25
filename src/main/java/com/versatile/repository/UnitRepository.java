package com.versatile.repository;

import com.versatile.domain.Unit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Unit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {}
