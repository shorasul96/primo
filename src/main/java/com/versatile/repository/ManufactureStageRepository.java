package com.versatile.repository;

import com.versatile.domain.ManufactureStage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ManufactureStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManufactureStageRepository extends JpaRepository<ManufactureStage, Long> {}
