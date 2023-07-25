package com.versatile.repository;

import com.versatile.domain.Marketing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Marketing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketingRepository extends JpaRepository<Marketing, Long> {}
