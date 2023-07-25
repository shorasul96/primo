package com.versatile.repository;

import com.versatile.domain.TransactionHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransactionHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {}
