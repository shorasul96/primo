package com.versatile.repository;

import com.versatile.domain.PartialObtain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartialObtain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartialObtainRepository extends JpaRepository<PartialObtain, Long> {}
